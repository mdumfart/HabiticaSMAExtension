package com.example.habiticasmaextension.core.proxy;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;

import com.example.habiticasmaextension.core.models.GroupMember;
import com.example.habiticasmaextension.core.models.Stats;
import com.example.habiticasmaextension.core.models.ThinQuest;
import com.example.habiticasmaextension.core.models.User;
import com.example.habiticasmaextension.core.services.PreferencesService;
import com.example.habiticasmaextension.core.services.PreferencesServiceFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class HabiticaProxyImpl implements HabiticaProxy {
    private static final String SERVICE_BASE_URL = "https://habitica.com/api/v3/";
    private static final String X_CLIENT = "42a8c893-3efd-483f-a284-79b2c6e86d96-SMAHabiticaExtension";
    private static final String CHAT_API_PREFIX ="@habiticaExt:";
    private static final int API_KEY_LENGTH = 36;

    private String userId;
    private String apiToken;

    public HabiticaProxyImpl(String userId, String apiToken){
        this.userId = userId;
        this.apiToken = apiToken;
    }

    private final Proxy proxy = new Retrofit.Builder()
            .baseUrl(SERVICE_BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient())
            .build()
            .create(Proxy.class);

    private OkHttpClient getClient() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.addInterceptor(chain -> {
            Request request = chain.request().newBuilder()
                    .addHeader("x-client", X_CLIENT)
                    .addHeader("x-api-user", userId)
                    .addHeader("x-api-key", apiToken).build();
            return chain.proceed(request);
        });

        return builder.build();
    }

    @Override
    public User getUserDetails() throws IOException {
        Proxy_UserDetails proxyUserDetails = proxy.getUserDetails().execute().body();

        if (proxyUserDetails != null) {
            String classString = proxyUserDetails.data.stats.klass.substring(0, 1).toUpperCase() + proxyUserDetails.data.stats.klass.substring(1);

            List<String> questStringList = new LinkedList<String>();

            for (String questString : proxyUserDetails.data.achievements.quests.keySet()) {
                questStringList.add(questString);
            }

            return new User(
                    userId,
                    apiToken,
                    proxyUserDetails.data.auth.local.username,
                    new com.example.habiticasmaextension.core.models.Stats(
                            proxyUserDetails.data.stats.hp,
                            proxyUserDetails.data.stats.lvl,
                            classString
                    ),
                    proxyUserDetails.data.party._id,
                    questStringList
                    );
        }

        return null;
    }

    @Override
    public List<GroupMember> getGroupMembers(String groupId, Context ctx) throws IOException {
        Proxy_GroupMembers proxyGroupMembers = proxy.getGroupMembers("groups/" + groupId + "/members").execute().body();

        List<GroupMember> members = new ArrayList<GroupMember>();

        for (UserInGroup member : proxyGroupMembers.data){
            // Try if there is api key in preferences
            String apiKey = PreferencesServiceFactory.createService().get(member.id, ctx);

            if (member.id.equals(PreferencesServiceFactory.createService().get("userId", ctx))){
                apiKey = PreferencesServiceFactory.createService().get("apiToken", ctx);
            }

            members.add(new GroupMember(member.profile.name, member.id, apiKey));
        }

        return members;
    }

    @Override
    public Map<String, String> getApiKeysFromChat(String groupId, Context ctx) throws IOException {
        Proxy_GroupChat proxyGroupChat = proxy.getGroupChat("groups/" + groupId + "/chat").execute().body();

        Map<String, String> keyMap = new ArrayMap<String, String>();

        for(ChatData data : proxyGroupChat.data){
            if (data.text.contains(CHAT_API_PREFIX)){
                int dividerIndex = data.text.indexOf(":");

                String apiKey = data.text.substring(dividerIndex + 1, dividerIndex + 1 +API_KEY_LENGTH);

                if (PreferencesServiceFactory.createService().get(data.uuid, ctx).isEmpty()) {
                    keyMap.put(data.uuid, apiKey);
                }
            }
        }

        return keyMap;
    }

    @Override
    public List<ThinQuest> getAllQuests() throws IOException {
        Proxy_Quests proxyQuests = proxy.getQuests().execute().body();

        List<ThinQuest> quests = new LinkedList<ThinQuest>();

        for(Map.Entry<String, Quest> entry : proxyQuests.data.quests.entrySet()) {
            quests.add(new ThinQuest(entry.getValue().key, entry.getValue().text));
        }

        return quests;
    }
}

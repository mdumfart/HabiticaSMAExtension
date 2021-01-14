package com.example.habiticasmaextension.core.proxy;

import android.util.Log;

import com.example.habiticasmaextension.core.models.GroupMember;
import com.example.habiticasmaextension.core.models.Stats;
import com.example.habiticasmaextension.core.models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class HabiticaProxyImpl implements HabiticaProxy {
    private static final String SERVICE_BASE_URL = "https://habitica.com/api/v3/";
    private static final String X_CLIENT = "42a8c893-3efd-483f-a284-79b2c6e86d96-SMAHabiticaExtension";

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

            return new User(
                    userId,
                    apiToken,
                    proxyUserDetails.data.auth.local.username,
                    new com.example.habiticasmaextension.core.models.Stats(
                            proxyUserDetails.data.stats.hp,
                            proxyUserDetails.data.stats.lvl,
                            classString
                    ),
                    proxyUserDetails.data.party._id);
        }

        return null;
    }

    @Override
    public List<GroupMember> getGroupMembers(String groupId) throws IOException {
        Proxy_GroupMembers proxyGroupMembers = proxy.getGroupMembers("groups/" + groupId + "/members").execute().body();

        List<GroupMember> members = new ArrayList<GroupMember>();

        for (UserInGroup member : proxyGroupMembers.data){
            members.add(new GroupMember(member.profile.name, member.id, ""));
        }

        return members;
    }
}

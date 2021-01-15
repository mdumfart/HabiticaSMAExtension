package com.example.habiticasmaextension.core.proxy;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Proxy {
    @GET("user?userFields=party.quest,party._id,stats,achievements.quests")
    Call<Proxy_UserDetails> getUserDetails();

    @GET()
    Call<Proxy_GroupMembers> getGroupMembers(@Url String url);

    @GET()
    Call<Proxy_GroupChat> getGroupChat(@Url String url);

    @GET("content")
    Call<Proxy_Quests> getQuests();
}

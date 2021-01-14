package com.example.habiticasmaextension.core.proxy;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Proxy {
    @GET("user?userFields=party.quest,party._id,stats")
    Call<Proxy_UserDetails> getUserDetails();

    @GET()
    Call<Proxy_GroupMembers> getGroupMembers(@Url String url);
}

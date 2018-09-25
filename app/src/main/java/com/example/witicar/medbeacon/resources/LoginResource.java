package com.example.witicar.medbeacon.resources;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LoginResource {

    @GET("/patient/login")
    Call<String> getLoginData(@Query("login") String login);

}

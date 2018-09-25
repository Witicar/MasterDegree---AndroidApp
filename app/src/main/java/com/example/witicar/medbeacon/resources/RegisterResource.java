package com.example.witicar.medbeacon.resources;

import com.example.witicar.medbeacon.models.Address;
import com.example.witicar.medbeacon.models.Patient;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RegisterResource {

    @POST("patient/register")
    Call<Patient> postPatientData(@Body Patient patient);

    @GET("patient/register/login")
    Call<Boolean> isRegisterLoginMailExist(@Query("login") String login,@Query("mail") String mail);
}

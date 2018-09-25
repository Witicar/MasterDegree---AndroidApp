package com.example.witicar.medbeacon.resources;

import com.example.witicar.medbeacon.models.Address;
import com.example.witicar.medbeacon.models.Patient;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface AccountResource {

    @PUT("account/patient")
    Call<Patient> putPatientData(@Query("login") String login, @Body Patient patient);

    @GET("account/address")
    Call<Address> getAdressData(@Query("login") String login);

}

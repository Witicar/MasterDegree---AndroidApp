package com.example.witicar.medbeacon.resources;

import com.example.witicar.medbeacon.models.Doctor;
import com.example.witicar.medbeacon.models.Patient;
import com.example.witicar.medbeacon.models.Visit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RegistrationResource {

    @GET("/registration/doctors")
    Call<List<Doctor>> getDoctors();

    @GET("/registration/visites")
    Call<List<Visit>> getVisites(@Query("login") String login);

    @POST("/registration")
    Call<Visit> postVisit(@Body Visit visit, @Query("login") String login,@Query("doctor_id") long doctor_id);

    @GET("/registration/patient")
    Call<Patient> getPatient(@Query("login") String login);

    @PUT("/registration/visites")
    Call<List<Visit>> putVisites(@Query("hour") int hour);
}

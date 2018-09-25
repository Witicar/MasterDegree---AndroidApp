package com.example.witicar.medbeacon.resources;

import com.example.witicar.medbeacon.models.MedicalTestRegistration;
import com.example.witicar.medbeacon.models.Patient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface MedicalTestResource {

    @GET("medicalregistration")
    Call<List<MedicalTestRegistration>> getMedicalTestRegistration();

    @PUT("medicalregistration/patient")
    Call<Patient> putMedicalTestRegistration(@Body Patient patient);

    @PUT("medicalregistration/move")
    Call<List<Patient>> moveMedicalTestRegistration(@Query("position") Integer position);

    @DELETE("medicalregistration/delete")
    Call<Void> deleteMedicalTestRegistration();

}

package com.example.witicar.medbeacon.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.witicar.medbeacon.R;
import com.example.witicar.medbeacon.models.Address;
import com.example.witicar.medbeacon.models.Patient;
import com.example.witicar.medbeacon.resources.AccountResource;
import com.example.witicar.medbeacon.resources.RegisterResource;
import com.example.witicar.medbeacon.resources.RegistrationResource;
import com.example.witicar.medbeacon.retrofitConfiguration.RetrofitConfiguration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Account extends Activity {

    private String currentPatientLogin;

    EditText phoneNumber;
    Spinner priority;
    EditText country ;
    EditText voivodeship ;
    EditText city ;
    EditText postalCode;
    EditText houseNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Intent intent = getIntent();
        currentPatientLogin = intent.getStringExtra("login");

        phoneNumber = findViewById(R.id.account_phone_number_field);
        priority = findViewById(R.id.account_priority_field);
        country = findViewById(R.id.account_country_field);
        voivodeship = findViewById(R.id.account_voivodeship_field);
        city = findViewById(R.id.account_city_field);
        postalCode = findViewById(R.id.account_postal_code_field);
        houseNumber = findViewById(R.id.account_house_number_field);

        Retrofit retrofit = RetrofitConfiguration.startRetrofit();
        AccountResource accountResource = retrofit.create(AccountResource.class);
        Call<Address> call = accountResource.getAdressData(currentPatientLogin);
        call.enqueue(new Callback<Address>() {
            @Override
            public void onResponse(Call<Address> call, Response<Address> response) {

                Address a = response.body();

                country.setText(a.getCountry());
                voivodeship.setText(a.getVoivodeship());
                city.setText(a.getCity());
                postalCode.setText(a.getPostalcode());
                houseNumber.setText(a.getHouseNumber());
            }

            @Override
            public void onFailure(Call<Address> call, Throwable t) {

            }
        });

        RegistrationResource registrationResource = retrofit.create(RegistrationResource.class);
        Call<Patient> patientCall = registrationResource.getPatient(currentPatientLogin);
        patientCall.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                phoneNumber.setText(Long.toString(response.body().getPhoneNumber()));
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {

            }
        });

    }

    public void doChange(View view) {

        long phoneNumberValue = Long.parseLong(phoneNumber.getText().toString());
        String stringPriority = priority.getSelectedItem().toString();
        String stringCountry = country.getText().toString();
        String stringVoivodeship = voivodeship.getText().toString();
        String stringCity = city.getText().toString();
        String stringPostalCode = postalCode.getText().toString();
        String stringHouseNumber = houseNumber.getText().toString();

        Patient patient = new Patient();
        patient.setPriority(stringPriority);
        patient.setPhoneNumber(phoneNumberValue);

        Address address = new Address();
        address.setCountry(stringCountry);
        address.setVoivodeship(stringVoivodeship);
        address.setCity(stringCity);
        address.setPostalcode(stringPostalCode);
        address.setHouseNumber(stringHouseNumber);

        patient.setAddress_patient(address);

        Retrofit retrofit = RetrofitConfiguration.startRetrofit();
        final AccountResource accountResource = retrofit.create(AccountResource.class);

        Call<Patient> call = accountResource.putPatientData(currentPatientLogin ,patient);
        call.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {

            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Toast.makeText(Account.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
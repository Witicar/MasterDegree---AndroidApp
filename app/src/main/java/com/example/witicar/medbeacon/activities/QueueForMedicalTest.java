package com.example.witicar.medbeacon.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.witicar.medbeacon.R;
import com.example.witicar.medbeacon.models.MedicalTestRegistration;
import com.example.witicar.medbeacon.models.Patient;
import com.example.witicar.medbeacon.resources.MedicalTestResource;
import com.example.witicar.medbeacon.resources.RegistrationResource;
import com.example.witicar.medbeacon.retrofitConfiguration.RetrofitConfiguration;
import com.kontakt.sdk.android.ble.connection.OnServiceReadyListener;
import com.kontakt.sdk.android.ble.manager.ProximityManager;
import com.kontakt.sdk.android.ble.manager.ProximityManagerFactory;
import com.kontakt.sdk.android.ble.manager.listeners.IBeaconListener;
import com.kontakt.sdk.android.ble.manager.listeners.simple.SimpleIBeaconListener;
import com.kontakt.sdk.android.common.KontaktSDK;
import com.kontakt.sdk.android.common.profile.IBeaconDevice;
import com.kontakt.sdk.android.common.profile.IBeaconRegion;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class QueueForMedicalTest extends Activity {

    private String currentPatientLogin;
    private Patient currentPatient;

    private TextView description, peopleInFrontOfYou;
    private Button register, nextPatient;

    private ProximityManager proximityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_test_registration);

        Intent intent = getIntent();
        currentPatientLogin = intent.getStringExtra("login");

        description = findViewById(R.id.test_queue_text);
        peopleInFrontOfYou = findViewById(R.id.test_queue);
        register = findViewById(R.id.test_registration);
        nextPatient = findViewById(R.id.nextPatient);

        getCurrentPatientAndPriority();

        register.setClickable(false);
        register.setBackgroundColor(Color.LTGRAY);

        KontaktSDK.initialize("MedBeaconKey");
        proximityManager = ProximityManagerFactory.create(getApplicationContext());
        proximityManager.setIBeaconListener(createIBeaconListener());
        startScanning();

    }

    public void startScanning() {

        proximityManager.connect(new OnServiceReadyListener() {
            @Override
            public void onServiceReady() {
                proximityManager.startScanning();
            }
        });
    }

    private IBeaconListener createIBeaconListener() {
        return new SimpleIBeaconListener() {
            @Override
            public void onIBeaconDiscovered(IBeaconDevice ibeacon, IBeaconRegion region) {
                register.setClickable(true);
                register.setBackgroundColor(Color.DKGRAY);
            }

            @Override
            public void onIBeaconLost(IBeaconDevice ibeacon, IBeaconRegion region) {
                register.setClickable(false);
                register.setBackgroundColor(Color.LTGRAY);
            }
        };
    }

    public void testRegistration(View view) {

        description.setVisibility(View.VISIBLE);
        peopleInFrontOfYou.setVisibility(View.VISIBLE);

        Retrofit retrofit = RetrofitConfiguration.startRetrofit();
        final MedicalTestResource medicalTestResource = retrofit.create(MedicalTestResource.class);

        Call<List<MedicalTestRegistration>> call = medicalTestResource.getMedicalTestRegistration();
        call.enqueue(new Callback<List<MedicalTestRegistration>>() {
            @Override
            public void onResponse(Call<List<MedicalTestRegistration>> call, Response<List<MedicalTestRegistration>> response) {

                final MedicalTestRegistration medicalTestRegistration = new MedicalTestRegistration();
                int position;

                if(response.body().isEmpty()) {
                    position = 1;
                    medicalTestRegistration.setPosition(position);
                    medicalTestRegistration.setPriority(currentPatient.getPriority());
                    currentPatient.setMedicalTestRegistration_patient(medicalTestRegistration);
                    System.out.println("Empty: " + position);
                } else
                {
                    if(currentPatient.getPriority() == "Ciąża")
                    {
                        int counter = 0;
                        for( MedicalTestRegistration m : response.body())
                        {
                            if(m.getPriority() == currentPatient.getPriority())
                                counter++;
                        }
                        position = counter + 1;
                        medicalTestRegistration.setPosition(position);
                        medicalTestRegistration.setPriority(currentPatient.getPriority());
                        currentPatient.setMedicalTestRegistration_patient(medicalTestRegistration);
                        System.out.println("Ciąża: " + position);

                    } else if (currentPatient.getPriority() == "Honorowy dawca krwi")
                    {
                        int counter = 0;
                        for( MedicalTestRegistration m : response.body())
                        {
                            if(m.getPriority() == currentPatient.getPriority())
                                counter++;
                            if(m.getPriority() == "Ciąża")
                                counter++;
                        }
                        position = counter + 1;
                        medicalTestRegistration.setPosition(position);
                        medicalTestRegistration.setPriority(currentPatient.getPriority());
                        currentPatient.setMedicalTestRegistration_patient(medicalTestRegistration);
                        System.out.println("Honorowy: " + position);
                    } else if (currentPatient.getPriority() == "Zaawansowany wiek")
                    {
                        int counter = 0;
                        for( MedicalTestRegistration m : response.body())
                        {
                            if(m.getPriority() == currentPatient.getPriority())
                                counter++;
                            if(m.getPriority() == "Honorowy dawca krwi")
                                counter++;
                            if(m.getPriority() == "Ciąża")
                                counter++;
                        }
                        position = counter + 1;
                        medicalTestRegistration.setPosition(position);
                        medicalTestRegistration.setPriority(currentPatient.getPriority());
                        currentPatient.setMedicalTestRegistration_patient(medicalTestRegistration);
                        System.out.println("Wiek: " + position);
                    } else
                    {
                        position = response.body().size();
                        medicalTestRegistration.setPosition(position);
                        medicalTestRegistration.setPriority(currentPatient.getPriority());
                        currentPatient.setMedicalTestRegistration_patient(medicalTestRegistration);
                        System.out.println("Brak: " + position);
                    }
                }

                Call<List<Patient>> moveCall = medicalTestResource.moveMedicalTestRegistration(position);
                moveCall.enqueue(new Callback<List<Patient>>() {
                    @Override
                    public void onResponse(Call<List<Patient>> call, Response<List<Patient>> responser) {
                        Call<Patient> patientCall = medicalTestResource.putMedicalTestRegistration(currentPatient);
                        patientCall.enqueue(new Callback<Patient>() {
                            @Override
                            public void onResponse(Call<Patient> call, Response<Patient> response) {
                                peopleInFrontOfYou.setText(Integer.toString(response.body().getMedicalTestRegistration_patient().getPosition()-1));
                                register.setClickable(false);
                                register.setBackgroundColor(Color.LTGRAY);
                            }

                            @Override
                            public void onFailure(Call<Patient> call, Throwable t) {
                                Toast.makeText(QueueForMedicalTest.this, t.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<List<Patient>> call, Throwable t) {
                        Toast.makeText(QueueForMedicalTest.this, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onFailure(Call<List<MedicalTestRegistration>> call, Throwable t) {
                Toast.makeText(QueueForMedicalTest.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void nextPatient(View view) {

        Retrofit retrofit = RetrofitConfiguration.startRetrofit();
        MedicalTestResource medicalTestResource = retrofit.create(MedicalTestResource.class);

        Call<Void> call = medicalTestResource.deleteMedicalTestRegistration();
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(Integer.parseInt(peopleInFrontOfYou.getText().toString()) == 0)
                {
                    description.setVisibility(View.INVISIBLE);
                    peopleInFrontOfYou.setVisibility(View.INVISIBLE);
                    peopleInFrontOfYou.setText("");
                    register.setClickable(true);
                    register.setBackgroundColor(Color.DKGRAY);
                }
                else
                    peopleInFrontOfYou.setText(Integer.parseInt(peopleInFrontOfYou.getText().toString())-1);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void getCurrentPatientAndPriority()
    {
        Retrofit retrofit = RetrofitConfiguration.startRetrofit();
        final RegistrationResource registrationResource = retrofit.create(RegistrationResource.class);

        Call<Patient> call = registrationResource.getPatient(currentPatientLogin);
        call.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                currentPatient = response.body();
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Toast.makeText(QueueForMedicalTest.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

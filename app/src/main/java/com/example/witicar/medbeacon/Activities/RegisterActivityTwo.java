package com.example.witicar.medbeacon.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.witicar.medbeacon.Models.Patient;
import com.example.witicar.medbeacon.R;
import com.example.witicar.medbeacon.Resources.RegisterResource;
import com.example.witicar.medbeacon.RetrofitConfiguration.RetrofitConfiguration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivityTwo extends Activity {

    private Calendar calendar;
    private Intent intent;
    private SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);

        calendar = Calendar.getInstance();
        intent = getIntent();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    }

    public void doRegister(View view)
    {
        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");
        String mail = intent.getStringExtra("mail");

        TextView textFirstName = findViewById(R.id.register_wrong_first_name);
        TextView textSecondName = findViewById(R.id.register_wrong_second_name);
        TextView textBirthDate = findViewById(R.id.register_wrong_birth_date);
        TextView textPlaceOfBirth = findViewById(R.id.register_wrong_place_of_birth);
        TextView textPersonalIdentityNumber = findViewById(R.id.register_wrong_personal_identity_number);
        TextView textSex = findViewById(R.id.register_wrong_sex);
        TextView textPriority = findViewById(R.id.register_wrong_priority);

        EditText firstName = findViewById(R.id.register_first_name_field);
        String stringFirstName = firstName.getText().toString();

        EditText secondName = findViewById(R.id.register_second_name_field);
        String stringSecondName = secondName.getText().toString();

        EditText birthDate = findViewById(R.id.register_birth_date_field);
        Date dateBirthDate = null;
        try {
            dateBirthDate = simpleDateFormat.parse(birthDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        EditText placeOfBirth = findViewById(R.id.register_place_of_birth_field);
        String stringPlaceOfBirth = placeOfBirth.getText().toString();

        EditText personalIdentityNumber = findViewById(R.id.register_personal_identity_number_field);
        int stringPersonalIdentityNumber;
        if(TextUtils.isEmpty(personalIdentityNumber.getText().toString()))
            stringPersonalIdentityNumber = 0;
        else
            stringPersonalIdentityNumber = Integer.parseInt(personalIdentityNumber.getText().toString());

        Spinner sex = findViewById(R.id.register_sex_field);
        String stringSex = sex.getSelectedItem().toString();

        Spinner priority = findViewById(R.id.register_priority_field);
        String stringPriority = priority.getSelectedItem().toString();

        textFirstName.setVisibility(View.INVISIBLE);
        textSecondName.setVisibility(View.INVISIBLE);
        textBirthDate.setVisibility(View.INVISIBLE);
        textPlaceOfBirth.setVisibility(View.INVISIBLE);
        textPersonalIdentityNumber.setVisibility(View.INVISIBLE);
        textSex.setVisibility(View.INVISIBLE);
        textPriority.setVisibility(View.INVISIBLE);

        boolean isCorrect = true;

        if(stringFirstName.equals("")){
            textFirstName.setVisibility(View.VISIBLE);
            isCorrect = false;
        }
        if(stringSecondName.equals("")){
            textSecondName.setVisibility(View.VISIBLE);
            isCorrect = false;
        }
        if (birthDate.getText().equals("")){
        textBirthDate.setVisibility(View.VISIBLE);
        isCorrect = false;
        }
        if (stringPlaceOfBirth.equals("")){
            textPlaceOfBirth.setVisibility(View.VISIBLE);
            isCorrect = false;
        }
        if (stringPersonalIdentityNumber == 0){
            textPersonalIdentityNumber.setVisibility(View.VISIBLE);
            isCorrect = false;
        }
        if (stringSex.equals("")) {
            textSex.setVisibility(View.VISIBLE);
            isCorrect = false;
        }
        if (stringPriority.equals("")) {
            textSex.setVisibility(View.VISIBLE);
            isCorrect = false;
        }
        if (isCorrect){

            Patient patient = new Patient();
            patient.setLogin(username);
            patient.setPassword(password);
            patient.setEmail(mail);
            patient.setName(stringFirstName);
            patient.setSurname(stringSecondName);
            patient.setBirthDate(dateBirthDate);
            patient.setPlaceOfBirth(stringPlaceOfBirth);
            patient.setPesel(stringPersonalIdentityNumber);
            patient.setSex(stringSex);
            patient.setPriority(stringPriority);

            Retrofit retrofit = RetrofitConfiguration.startRetrofit();
            final RegisterResource clientInterface = retrofit.create(RegisterResource.class);

            Call<Patient> call = clientInterface.postPatientData(patient);

            System.out.println(RetrofitConfiguration.gson.toJson(patient));
            call.enqueue(new Callback<Patient>() {
                @Override
                public void onResponse(Call<Patient> call, Response<Patient> response) {
                    System.out.println(response);
                    Intent intent = new Intent(RegisterActivityTwo.this, WelcomeActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<Patient> call, Throwable t) {
                    Toast.makeText(RegisterActivityTwo.this, t.toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void openCalendar(View view)
    {
        final EditText birth_date= findViewById(R.id.register_birth_date_field);

        DatePickerDialog.OnDateSetListener datePickerDialog = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                birth_date.setText(simpleDateFormat.format(calendar.getTime()));

            }

        };

        new DatePickerDialog(RegisterActivityTwo.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, datePickerDialog, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();

    }
}

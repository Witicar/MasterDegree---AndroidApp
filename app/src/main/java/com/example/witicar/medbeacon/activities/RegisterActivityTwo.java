package com.example.witicar.medbeacon.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.witicar.medbeacon.models.Address;
import com.example.witicar.medbeacon.models.Patient;
import com.example.witicar.medbeacon.R;
import com.example.witicar.medbeacon.resources.RegisterResource;
import com.example.witicar.medbeacon.retrofitConfiguration.RetrofitConfiguration;

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

    public void doRegister(View view) {
        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");
        String mail = intent.getStringExtra("mail");

        TextView textFirstName = findViewById(R.id.register_wrong_first_name);
        TextView textSecondName = findViewById(R.id.register_wrong_second_name);
        TextView textBirthDate = findViewById(R.id.register_wrong_birth_date);
        TextView textPersonalIdentityNumber = findViewById(R.id.register_wrong_personal_identity_number);
        TextView textPhoneNumber = findViewById(R.id.register_wrong_phone_number);
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
        long personalIdentityNumberValue = Long.parseLong(personalIdentityNumber.getText().toString());

        EditText phoneNumber = findViewById(R.id.register_phone_number_field);
        long phoneNumberValue = Long.parseLong(phoneNumber.getText().toString());

        Spinner sex = findViewById(R.id.register_sex_field);
        String stringSex = sex.getSelectedItem().toString();

        Spinner priority = findViewById(R.id.register_priority_field);
        String stringPriority = priority.getSelectedItem().toString();

        EditText country = findViewById(R.id.register_country_field);
        String stringCountry = country.getText().toString();

        EditText voivodeship = findViewById(R.id.register_voivodeship_field);
        String stringVoivodeship = voivodeship.getText().toString();

        EditText city = findViewById(R.id.register_city_field);
        String stringCity = city.getText().toString();

        EditText postalCode = findViewById(R.id.register_postal_code_field);
        String stringPostalCode = postalCode.getText().toString();

        EditText houseNumber = findViewById(R.id.register_house_number_field);
        String stringHouseNumber = houseNumber.getText().toString();

        textFirstName.setVisibility(View.INVISIBLE);
        textSecondName.setVisibility(View.INVISIBLE);
        textBirthDate.setVisibility(View.INVISIBLE);
        textPersonalIdentityNumber.setVisibility(View.INVISIBLE);
        textPhoneNumber.setVisibility(View.INVISIBLE);
        textSex.setVisibility(View.INVISIBLE);
        textPriority.setVisibility(View.INVISIBLE);

        boolean isCorrect = true;

        if (stringFirstName.equals("")) {
            textFirstName.setVisibility(View.VISIBLE);
            isCorrect = false;
        }
        if (stringSecondName.equals("")) {
            textSecondName.setVisibility(View.VISIBLE);
            isCorrect = false;
        }
        if (birthDate.getText().equals("")) {
            textBirthDate.setVisibility(View.VISIBLE);
            isCorrect = false;
        }
        if (personalIdentityNumberValue == 0) {
            textPersonalIdentityNumber.setVisibility(View.VISIBLE);
            isCorrect = false;
        }
        if (phoneNumberValue == 0) {
            textPhoneNumber.setVisibility(View.VISIBLE);
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
        if (isCorrect) {

            Patient patient = new Patient();
            patient.setLogin(username);
            patient.setPassword(password);
            patient.setEmail(mail);
            patient.setName(stringFirstName);
            patient.setSurname(stringSecondName);
            patient.setBirthDate(dateBirthDate);
            patient.setPlaceOfBirth(stringPlaceOfBirth);
            patient.setPesel(personalIdentityNumberValue);
            patient.setSex(stringSex);
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
            final RegisterResource registerResource = retrofit.create(RegisterResource.class);

            Call<Patient> call = registerResource.postPatientData(patient);
            call.enqueue(new Callback<Patient>() {
                @Override
                public void onResponse(Call<Patient> call, Response<Patient> response) {
                    Intent intent = new Intent(RegisterActivityTwo.this, WelcomeActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<Patient> call, Throwable t) {
                    Toast.makeText(RegisterActivityTwo.this, t.toString(), Toast.LENGTH_SHORT).show();
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

package com.example.witicar.medbeacon.activities;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.witicar.medbeacon.beaconService.BeaconConnector;
import com.example.witicar.medbeacon.models.Doctor;
import com.example.witicar.medbeacon.models.HoursOfAdmission;
import com.example.witicar.medbeacon.models.Patient;
import com.example.witicar.medbeacon.models.Visit;
import com.example.witicar.medbeacon.R;
import com.example.witicar.medbeacon.resources.RegistrationResource;
import com.example.witicar.medbeacon.retrofitConfiguration.RetrofitConfiguration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Registration extends Activity {

    private List<String> itemList_doctor = new ArrayList<>();
    private List<String> itemList_term = new ArrayList<>();

    private List<HoursOfAdmission> hoursList = new ArrayList<>();
    private List<Doctor> doctorList = new ArrayList<>();

    private Spinner doctorSpinner, termSpinner;

    private List<List<Visit>> visitList = new ArrayList<>();

    private String currentPatientLogin;

    private List<List<Integer>> listOfVisitTimes = new ArrayList<>();

    private Patient currentPatient;
    private String priority;

    private EditText fakeTimeField;
    private Spinner spinnerDayOfWeek;

    private int currentFakeTime;

    private Calendar myCalender;
    private SimpleDateFormat simpleDateFormat;

    ///////////////////Need to be there because i have asynchronous method in method and they cannot be declared as final////////
    ///////////////////Used in registration Button OnClick event////////////////////////////////////////////////////////////////
    private long doctor_id;
    private Visit visit;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    boolean activateService = false;
    Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Intent intent = getIntent();
        currentPatientLogin = intent.getStringExtra("login");

        termSpinner = findViewById(R.id.registration_term_field);

        getFakeTimeAndWeekDay();
        getCurrentPatientAndPriority();
        setDoctorSpinner();
        setHourSpinnerAfterChangeDoctor();
        setHourSpinnerAfterChangeFakeDayOfWeek();
        setHourSpinnerAfterChangeFakeTime();

        serviceIntent = new Intent(Registration.this, BeaconConnector.class);
        serviceIntent.putExtra("login", currentPatientLogin);
    }


    public void registerOnClick(View view) {

        visit = new Visit();

        String time = termSpinner.getSelectedItem().toString();
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(simpleDateFormat.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int visitTime = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);

        visit.setVisitTime(visitTime);

        int counter = 0;
        doctor_id = 0;
        for (String s : itemList_doctor) {
            if (doctorSpinner.getSelectedItem().toString().equals(s)) {
                doctor_id = doctorList.get(counter).getDoctorID();
            }
            counter++;
        }

        Retrofit retrofit = RetrofitConfiguration.startRetrofit();
        final RegistrationResource registrationResource = retrofit.create(RegistrationResource.class);

        if (listOfVisitTimes.contains(visitTime)) {

            Call<List<Visit>> visitCall = registrationResource.putVisites(visitTime);
            visitCall.enqueue(new Callback<List<Visit>>() {
                @Override
                public void onResponse(Call<List<Visit>> caller, Response<List<Visit>> response) {
                    Call<Visit> call = registrationResource.postVisit(visit, currentPatientLogin, doctor_id);
                    call.enqueue(new Callback<Visit>() {
                        @Override
                        public void onResponse(Call<Visit> call, Response<Visit> response) {
                            Toast.makeText(Registration.this,
                                    "Zostałeś zarejestrowany do " + doctorSpinner.getSelectedItem().toString() + " na godzinę " + termSpinner.getSelectedItem().toString(),
                                    Toast.LENGTH_LONG).show();

                            termSet();
                        }

                        @Override
                        public void onFailure(Call<Visit> call, Throwable t) {

                        }
                    });
                }

                @Override
                public void onFailure(Call<List<Visit>> call, Throwable t) {

                }
            });

        } else {
            Call<Visit> call = registrationResource.postVisit(visit, currentPatientLogin, doctor_id);
            call.enqueue(new Callback<Visit>() {
                @Override
                public void onResponse(Call<Visit> call, Response<Visit> response) {
                    Toast.makeText(Registration.this,
                            "Zostałeś zarejestrowany do " + doctorSpinner.getSelectedItem().toString() + " na godzinę " + termSpinner.getSelectedItem().toString(),
                            Toast.LENGTH_LONG).show();

                    termSet();
                }

                @Override
                public void onFailure(Call<Visit> call, Throwable t) {

                }
            });
        }
    }

    private void setDoctorSpinner() {

        doctorSpinner = findViewById(R.id.registration_doctor_field);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, itemList_doctor);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        doctorSpinner.setAdapter(adapter);

        Retrofit retrofit = RetrofitConfiguration.startRetrofit();
        final RegistrationResource registrationResource = retrofit.create(RegistrationResource.class);

        Call<List<Doctor>> call = registrationResource.getDoctors();
        call.enqueue(new Callback<List<Doctor>>() {
            @Override
            public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
                doctorList = response.body();
                for (Doctor d : doctorList) {
                    itemList_doctor.add(d.getName() + " " + d.getSurname() + " (" + d.getSpecialization() + ")");
                    hoursList.add(d.getDoctor_hoursOfAdmission());
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Doctor>> call, Throwable t) {
                Toast.makeText(Registration.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setHourSpinnerAfterChangeFakeDayOfWeek() {
        spinnerDayOfWeek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                termSet();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setHourSpinnerAfterChangeDoctor() {
        doctorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                termSet();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
    }

    private void setHourSpinnerAfterChangeFakeTime() {
        fakeTimeField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                termSet();

                if(!activateService) {
                    serviceIntent.putExtra("fakeTime", currentFakeTime + "");
                    startService(serviceIntent);
                    activateService = true;
                }
            }
        });
    }

    private void termSet() {

        itemList_term.clear();

        for (int i = 0; i < doctorList.size(); i++) {
            if (doctorSpinner.getSelectedItem().toString()
                    .equals(doctorList.get(i).getName() + " " + doctorList.get(i).getSurname() + " (" + doctorList.get(i).getSpecialization() + ")")) {
                final ArrayAdapter<String> adapter = new ArrayAdapter<>(Registration.this, R.layout.spinner_item, itemList_term);
                adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                termSpinner.setAdapter(adapter);

                String t = fakeTimeField.getText().toString();
                Calendar calendar = Calendar.getInstance();
                try {
                    calendar.setTime(simpleDateFormat.parse(t));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                currentFakeTime = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);

                switch (spinnerDayOfWeek.getSelectedItem().toString()) {

                    case "Poniedziałek":
                        if (currentFakeTime < hoursList.get(i).getMonday_start())
                            setTermItemList(hoursList.get(i).getMonday_start(), hoursList.get(i).getMonday_end(), adapter);
                        else
                            setTermItemList(currentFakeTime - currentFakeTime % 15 + 15, hoursList.get(i).getMonday_end(), adapter);
                        break;
                    case "Wtorek":
                        if (currentFakeTime < hoursList.get(i).getThuesday_start())
                            setTermItemList(hoursList.get(i).getThuesday_start(), hoursList.get(i).getThuesday_end(), adapter);
                        else
                            setTermItemList(currentFakeTime - currentFakeTime % 15 + 15, hoursList.get(i).getThuesday_end(), adapter);
                        break;
                    case "Środa":
                        if (currentFakeTime < hoursList.get(i).getWednesday_start())
                            setTermItemList(hoursList.get(i).getWednesday_start(), hoursList.get(i).getWednesday_end(), adapter);
                        else
                            setTermItemList(currentFakeTime - currentFakeTime % 15 + 15, hoursList.get(i).getWednesday_end(), adapter);
                        break;
                    case "Czwartek":
                        if (currentFakeTime < hoursList.get(i).getThursday_start())
                            setTermItemList(hoursList.get(i).getThursday_start(), hoursList.get(i).getThursday_end(), adapter);
                        else
                            setTermItemList(currentFakeTime - currentFakeTime % 15 + 15, hoursList.get(i).getThursday_end(), adapter);
                        break;
                    case "Piątek":
                        if (currentFakeTime < hoursList.get(i).getFriday_start())
                            setTermItemList(hoursList.get(i).getFriday_start(), hoursList.get(i).getFriday_end(), adapter);
                        else
                            setTermItemList(currentFakeTime - currentFakeTime % 15 + 15, hoursList.get(i).getFriday_end(), adapter);
                        break;

                }
            }
        }
    }

    private void setTermItemList(final int start, final int end, final ArrayAdapter<String> adapter) {
        listOfVisitTimes.clear();
        visitList.clear();

        Retrofit retrofit = RetrofitConfiguration.startRetrofit();
        RegistrationResource registrationResource = retrofit.create(RegistrationResource.class);

        Call<List<Doctor>> call = registrationResource.getDoctors();
        call.enqueue(new Callback<List<Doctor>>() {
            @Override
            public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {

                int index = 0;
                listOfVisitTimes = Collect.asList(Collect.asList(0));
                listOfVisitTimes.get(0).clear();

                for (Doctor d : response.body()) {
                    visitList.add(d.getDoctorVisits());
                    for (Visit v: visitList.get(index)) {
                        listOfVisitTimes.get(index).add(v.getVisitTime());
                    }
                    index++;
                    listOfVisitTimes.add(Collect.asList(0));
                    listOfVisitTimes.get(index).clear();
                }

                int counter = 0;
                int currentPosition = 0;
                for (String s : itemList_doctor) {
                    if (doctorSpinner.getSelectedItem().toString().equals(s)) {
                        currentPosition = counter;
                    }
                    counter++;
                }

                if (priority.equals("Ciąża")) {
                    int difference = end - start;
                    for (int j = 0; j < difference; j += 15) {
                        if ((start + j) % 60 < 9)
                            itemList_term.add(Integer.toString((start + j) / 60) + ":0" + Integer.toString((start + j) % 60));
                        else
                            itemList_term.add(Integer.toString((start + j) / 60) + ":" + Integer.toString((start + j) % 60));
                    }
                    adapter.notifyDataSetChanged();
                }
                else if (priority.equals("Zaawansowany wiek")){
                    int difference = end - start;
                    for (int j = 0; j < difference; j += 15) {
                        if (listOfVisitTimes.get(currentPosition).contains(start + j) && (start+j)-currentFakeTime<120)
                            continue;
                        if ((start + j) % 60 < 9)
                            itemList_term.add(Integer.toString((start + j) / 60) + ":0" + Integer.toString((start + j) % 60));
                        else
                            itemList_term.add(Integer.toString((start + j) / 60) + ":" + Integer.toString((start + j) % 60));
                    }
                    adapter.notifyDataSetChanged();
                }
                else if (priority.equals("Honorowy dawca krwi")) {
                    int difference = end - start;
                    for (int j = 0; j < difference; j += 15) {
                        if (listOfVisitTimes.get(currentPosition).contains(start + j) && (start+j)-currentFakeTime<60)
                            continue;
                        if ((start + j) % 60 < 9)
                            itemList_term.add(Integer.toString((start + j) / 60) + ":0" + Integer.toString((start + j) % 60));
                        else
                            itemList_term.add(Integer.toString((start + j) / 60) + ":" + Integer.toString((start + j) % 60));
                    }
                    adapter.notifyDataSetChanged();
                }
                else {
                    int difference = end - start;
                    for (int j = 0; j < difference; j += 15) {
                        if (listOfVisitTimes.get(currentPosition).contains(start + j))
                            continue;
                        if ((start + j) % 60 < 9)
                            itemList_term.add(Integer.toString((start + j) / 60) + ":0" + Integer.toString((start + j) % 60));
                        else
                            itemList_term.add(Integer.toString((start + j) / 60) + ":" + Integer.toString((start + j) % 60));
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Doctor>> call, Throwable t) {
                Toast.makeText(Registration.this, t.toString(), Toast.LENGTH_SHORT).show();
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
                priority = currentPatient.getPriority();
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Toast.makeText(Registration.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getFakeTimeAndWeekDay()
    {
        fakeTimeField = findViewById(R.id.fake_time);
        spinnerDayOfWeek = findViewById(R.id.fake_weekDay);

        myCalender = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        fakeTimeField.setText(simpleDateFormat.format(myCalender.getTime()));

        spinnerDayOfWeek.setSelection(myCalender.get(Calendar.DAY_OF_WEEK) - 2);
    }

    public void setFakeTimeOnEditTextTimeClick(View view) {
        myCalender = Calendar.getInstance();
        final int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        final int minute = myCalender.get(Calendar.MINUTE);


        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfDay) {
                if (view.isShown()) {
                    myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    myCalender.set(Calendar.MINUTE, minuteOfDay);
                }
                fakeTimeField.setText(simpleDateFormat.format(myCalender.getTime()));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(Registration.this,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true);
        timePickerDialog.show();

    }
}

class Collect {
    public static <T> List<T> asList(T ... items) {
        List<T> list = new ArrayList<T>();
        for (T item : items) {
            list.add(item);
        }

        return list;
    }
}


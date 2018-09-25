package com.example.witicar.medbeacon.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.witicar.medbeacon.R;

public class WindowMainActivity extends Activity{

    String currentPatientLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_main);

        Intent intent = getIntent();
        currentPatientLogin = intent.getStringExtra("login");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    public void registration(View view)
    {
        Intent intent = new Intent(WindowMainActivity.this, Registration.class);
        intent.putExtra("login", currentPatientLogin);
        startActivity(intent);
    }

    public void account(View view)
    {
        Intent intent = new Intent(WindowMainActivity.this, Account.class);
        intent.putExtra("login", currentPatientLogin);
        startActivity(intent);
    }

    public void test(View view) {
        Intent intent = new Intent(WindowMainActivity.this, QueueForMedicalTest.class);
        intent.putExtra("login", currentPatientLogin);
        startActivity(intent);
    }
}

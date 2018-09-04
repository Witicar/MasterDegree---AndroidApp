package com.example.witicar.medbeacon.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.witicar.medbeacon.R;

public class WindowMainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_main);
    }

    public void registration(View view)
    {
        Intent intent = new Intent(WindowMainActivity.this, Registration.class);
        startActivity(intent);
    }

    public void callForHelp(View view)
    {
        Intent intent = new Intent(WindowMainActivity.this, CallForHelp.class);
        startActivity(intent);
    }

    public void account(View view)
    {
        Intent intent = new Intent(WindowMainActivity.this, Account.class);
        startActivity(intent);
    }
}

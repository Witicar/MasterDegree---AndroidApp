package com.example.witicar.medbeacon.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.witicar.medbeacon.resources.LoginResource;
import com.example.witicar.medbeacon.R;
import com.example.witicar.medbeacon.retrofitConfiguration.RetrofitConfiguration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WelcomeActivity extends Activity {

    private EditText login;
    private EditText password;
    private String login_text;
    private String password_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        1);
            }
        }
    }

    public void createAccount(View view) {
        Intent intent = new Intent(this, RegisterActivityOne.class);
        startActivity(intent);
    }

    public void login(View view) {
        login = findViewById(R.id.login_login_field);
        password = findViewById(R.id.login_password_field);
        login_text = login.getText().toString();
        password_text = password.getText().toString();

        TextView textLogin = findViewById(R.id.login_wrong_login);
        TextView textPassword = findViewById(R.id.login_wrong_password);

        textLogin.setVisibility(View.INVISIBLE);
        textPassword.setVisibility(View.INVISIBLE);

        boolean isCorrect = true;

        if (login_text.equals("")) {
            textLogin.setVisibility(View.VISIBLE);
            isCorrect = false;
        }
        if (password_text.equals("")) {
            textPassword.setVisibility(View.VISIBLE);
            isCorrect = false;
        }

        if (isCorrect) {
            Retrofit retrofit = RetrofitConfiguration.startRetrofit();

            final LoginResource clientInterface = retrofit.create(LoginResource.class);
            Call<String> call = clientInterface.getLoginData(login_text);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (password_text.equals(response.body())) {
                        Intent intent = new Intent(WelcomeActivity.this, WindowMainActivity.class);
                        intent.putExtra("login", login_text);
                        startActivity(intent);
                    } else {
                        Toast.makeText(WelcomeActivity.this, "Nieprawidłowy login lub hasło", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(WelcomeActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
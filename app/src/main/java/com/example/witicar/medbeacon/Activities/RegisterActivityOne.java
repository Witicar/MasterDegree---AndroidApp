package com.example.witicar.medbeacon.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.witicar.medbeacon.R;
import com.example.witicar.medbeacon.Resources.RegisterResource;
import com.example.witicar.medbeacon.RetrofitConfiguration.RetrofitConfiguration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivityOne extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);
    }

    public void registerNextStep(View view)
    {
        TextView textUsername = findViewById(R.id.register_wrong_username);
        TextView textPassword = findViewById(R.id.register_wrong_password);
        TextView textConfirmPassword = findViewById(R.id.register_wrong_confirm_password);
        TextView textMail = findViewById(R.id.register_wrong_mail);
        TextView textNotMatchedPasswords = findViewById(R.id.register_not_matched_password);

        EditText username = findViewById(R.id.register_username_field);
        final String stringUsername = username.getText().toString();

        EditText password = findViewById(R.id.register_password_field);
        final String stringPassword = password.getText().toString();

        EditText confirm_password = findViewById(R.id.register_confirm_password_field);
        final String stringConfirmPassword = confirm_password.getText().toString();

        EditText mail = findViewById(R.id.register_mail_field);
        final String stringMail = mail.getText().toString();

        textUsername.setVisibility(View.INVISIBLE);
        textPassword.setVisibility(View.INVISIBLE);
        textConfirmPassword.setVisibility(View.INVISIBLE);
        textMail.setVisibility(View.INVISIBLE);
        textNotMatchedPasswords.setVisibility(View.INVISIBLE);

        boolean isCorrect = true;

        if(stringUsername.equals("")){
            textUsername.setVisibility(View.VISIBLE);
            isCorrect = false;
        }
        if(stringPassword.equals("")){
            textPassword.setVisibility(View.VISIBLE);
            isCorrect = false;
        }
        if (stringConfirmPassword.equals("")){
            textConfirmPassword.setVisibility(View.VISIBLE);
            isCorrect = false;
        }
        if (stringMail.equals("")){
            textMail.setVisibility(View.VISIBLE);
            isCorrect = false;
        }
        if (!stringPassword.equals(stringConfirmPassword) && !stringPassword.equals("") && !stringConfirmPassword.equals("")){
            textNotMatchedPasswords.setVisibility(View.VISIBLE);
            isCorrect = false;
        }

        if (isCorrect) {

            Retrofit retrofit = RetrofitConfiguration.startRetrofit();
            final RegisterResource registerResource = retrofit.create(RegisterResource.class);
            Call<Boolean> call = registerResource.isRegisterLoginMailExist(stringUsername, stringMail);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.body().booleanValue() == Boolean.TRUE) {
                        Toast.makeText(RegisterActivityOne.this,
                                "Account with login or email is already exist",
                                Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        Intent intent = new Intent(RegisterActivityOne.this, RegisterActivityTwo.class);
                        intent.putExtra("username", stringUsername);
                        intent.putExtra("password", stringPassword);
                        intent.putExtra("confirm_password", stringConfirmPassword);
                        intent.putExtra("mail", stringMail);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(RegisterActivityOne.this, t.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}

package com.letsbook.letsbook.View.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.letsbook.letsbook.Model.Response_Login;
import com.letsbook.letsbook.R;
import com.letsbook.letsbook.Storage.SharedPrefManager;
import com.letsbook.letsbook.View.Activitys.Api.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private EditText edTxtemail, edTxtpassword;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edTxtemail = findViewById(R.id.login_email);
        edTxtpassword = findViewById(R.id.login_password);
    }

    public void login(View view) {
        userLogin();

    }

    private void userLogin() {

        String email = edTxtemail.getText().toString().trim();
        String password = edTxtpassword.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
        }else if (email.matches(emailPattern)){

            Call<Response_Login> call = ApiClient.getInstance().getApi().hitLoginApi(email, password);
            call.enqueue(new Callback<Response_Login>() {
                @Override
                public void onResponse(Call<Response_Login> call, Response<Response_Login> response) {
                    Response_Login responseLogin = response.body();
                    Log.e("response", response +"");

                    if (responseLogin.getCode() == 200) {
                        SharedPrefManager.getInstance(Login.this).saveUser(responseLogin.getData());
                        Intent intent = new Intent(Login.this, Home.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Toast.makeText(Login.this, "Login Successfully.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Login.this, responseLogin.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Response_Login> call, Throwable t) {
                    Toast.makeText(Login.this, "Please check your internet connection !!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
        }
    }


    public void OpenSignUpscreen(View view) {
        Intent intent = new Intent(Login.this, Signup.class);
        startActivity(intent);
        finish();
    }

    public void forgot_password(View view) {
        Intent intent = new Intent(Login.this, ForgotPassword.class);
        startActivity(intent);
    }

}
package com.letsbook.letsbook.View.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.letsbook.letsbook.Model.Response_Login;
import com.letsbook.letsbook.Model.Model_SignUp;
import com.letsbook.letsbook.R;
import com.letsbook.letsbook.Storage.SharedPrefManager;
import com.letsbook.letsbook.View.Activitys.Api.API_Interface;
import com.letsbook.letsbook.View.Activitys.Api.ApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup extends AppCompatActivity {

    private EditText edTxtname, edTxtemail, edTxtpassword, edTxtphone;
    public API_Interface apiInterface;
    public ApiClient apiClient;
    private ProgressDialog progress;
    private static final String SHARED_PREF_NAME = "my_shared_preff";

    private static SharedPrefManager mInstance;


    SharedPrefManager sharedPrefManager;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public static ArrayList<Model_SignUp> _modelSignUps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edTxtname = findViewById(R.id.signUp_first_and_last_name);
        edTxtemail = findViewById(R.id.signUp_email_address);
        edTxtpassword = findViewById(R.id.signUp_password);
        edTxtphone = findViewById(R.id.signUp_callNumber);

    }

    public void signup(View view) {
        userSignUp();
    }

    private void userSignUp() {
        String name = edTxtname.getText().toString().trim();
        String email = edTxtemail.getText().toString().trim();
        String password = edTxtpassword.getText().toString().trim();
        String phone = edTxtphone.getText().toString().trim();


        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
        } else if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 5) {
            Toast.makeText(this, "Password character should be more then 5", Toast.LENGTH_SHORT).show();
        } else if (phone.isEmpty()) {
            Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
        }else if (phone.length()<=9){
            Toast.makeText(this, "Please enter minimum 10 character", Toast.LENGTH_SHORT).show();
        } else if (email.matches(emailPattern)) {
            progress = new ProgressDialog(this);
            progress.setCancelable(false);// disable dismiss by tapping outside of the dialog
            progress.setMessage("Loading...");
            progress.show();

            Call<Response_Login> call = ApiClient.getInstance().getApi().hitSignUpApi(name, email, password, phone);
            call.enqueue(new Callback<Response_Login>() {
                @Override
                public void onResponse(Call<Response_Login> call, Response<Response_Login> response) {
                    Response_Login responseLogin = response.body();
                    if (responseLogin.getCode() == 200) {
                        progress.dismiss();
                        SharedPrefManager.getInstance(Signup.this).saveUser(responseLogin.getData());
                        Intent intent = new Intent(Signup.this, Home.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Toast.makeText(Signup.this, "Register Successfully.", Toast.LENGTH_SHORT).show();
                    } else {
                        progress.dismiss();
                        Toast.makeText(Signup.this, responseLogin.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Response_Login> call, Throwable t) {
                    progress.dismiss();
                    Toast.makeText(Signup.this, "Please check your internet connection !!", Toast.LENGTH_SHORT).show();
                }
            });


        } else {
            Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
        }
    }

    public void tap_here(View view) {
        startActivity(new Intent(Signup.this, Login.class));
        finish();
    }

}
package com.letsbook.letsbook.View.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.letsbook.letsbook.Model.Response_ForgotPassword;
import com.letsbook.letsbook.Model.Response_Global;
import com.letsbook.letsbook.R;
import com.letsbook.letsbook.View.Activitys.Api.ApiClient;
import com.letsbook.letsbook.View.Inner_Fragments.TermsAndConditions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity {

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private EditText  forgotPassword;
    private ImageView forgotPasswordBack;
    private ProgressDialog progress;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgotPassword = findViewById(R.id.forgotPassword);
        forgotPasswordBack = findViewById(R.id.forgot_password_back);

        forgotPasswordBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPassword.this, Login.class));
                finish();
            }
        });
    }

    public void Continue(View view) {
        forgotPassword();
    }

    private void forgotPassword(){
        String email = forgotPassword.getText().toString().trim();

        if (email.isEmpty()){
            Toast.makeText(this, "Please enter your email address !!!", Toast.LENGTH_SHORT).show();
        }else if (email.matches(emailPattern)) {
            progress = new ProgressDialog(ForgotPassword.this);
            progress.setCancelable(false);// disable dismiss by tapping outside of the dialog
            progress.setMessage("Loading...");
            progress.show();

            Call<Response_Global> call = ApiClient.getInstance().getApi().hitForgotPasswordApi(email);

            call.enqueue(new Callback<Response_Global>() {
                @Override
                public void onResponse(Call<Response_Global> call, Response<Response_Global> response) {

                    Response_Global response_global = response.body();
                    if (response_global.getCode() == 200) {
                        Intent intent = new Intent(ForgotPassword.this, Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        progress.dismiss();
                        Toast.makeText(ForgotPassword.this, "Please check you email to reset password!!", Toast.LENGTH_SHORT).show();
                    } else {
                        progress.dismiss();
                        Toast.makeText(ForgotPassword.this, "Invalid email", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Response_Global> call, Throwable t) {
                    progress.dismiss();
                    Toast.makeText(ForgotPassword.this, "Please check your internet connection !!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(),"Invalid email address",Toast.LENGTH_SHORT).show();
        }
    }

}
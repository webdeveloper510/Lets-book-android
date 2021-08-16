package com.letsbook.letsbook.View.Activitys;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.letsbook.letsbook.Model.GlobalData;
import com.letsbook.letsbook.R;
import com.letsbook.letsbook.Storage.SharedPrefManager;

public class SpleshScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splesh_screen);

         /*added this shared pref* to store the counter
         if the counter is grater then or = 15 application will crash*/

        //Get Preferenece
        SharedPreferences myPrefs;
        myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        int storedValue = myPrefs.getInt("counterValue", 0);

        storedValue++;
        Log.e("counterWatch", String.valueOf(storedValue));
        if (storedValue == 1) {
            SharedPrefManager.getInstance(this).clear();
        }

        if (storedValue >= 15) {
            throw new RuntimeException("This is a crash");
        }
        int screenCounter = 0;
        screenCounter = storedValue;


        //Set Preference
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor;
        prefsEditor = sharedPreferences.edit();
        //intVersionName->Any value to be stored
        prefsEditor.putInt("counterValue", screenCounter);
        prefsEditor.apply();


        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(2000);
                    /**
                     if user is logged in getting user info* from shared pref*
                     */
                    if (SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {
                        Intent intent = new Intent(SpleshScreen.this, Home.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        if (ActivityCompat.checkSelfPermission(SpleshScreen.this, Manifest
                                .permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            Intent intent = new Intent(SpleshScreen.this, Login.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(SpleshScreen.this, EnableLocationServices.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                } catch (InterruptedException e) {
                }

            }
        }).start();
    }
}
package com.letsbook.letsbook.View.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.letsbook.letsbook.R;

public class Success extends AppCompatActivity {

    private LinearLayout okGotIt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        okGotIt = findViewById(R.id.okay_got_it);

        okGotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Success.this, Home.class));
                finish();
            }
        });

    }
}
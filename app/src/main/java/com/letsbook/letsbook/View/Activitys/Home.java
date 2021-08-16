package com.letsbook.letsbook.View.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.letsbook.letsbook.R;
import com.letsbook.letsbook.Storage.SharedPrefManager;
import com.letsbook.letsbook.View.Inner_Fragments.TermsAndConditions;
import com.letsbook.letsbook.View.NavigationFragments.About_us_Fragment;
import com.letsbook.letsbook.View.NavigationFragments.Favorites;
import com.letsbook.letsbook.View.NavigationFragments.HomeFragment;
import com.letsbook.letsbook.View.NavigationFragments.My_order_Fragment;
import com.letsbook.letsbook.View.NavigationFragments.Profile_Fragment;

public class Home extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentTransaction fragmentTransaction;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new HomeFragment());
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_my_order:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new My_order_Fragment());
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_favorite:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new Favorites());
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_profile:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new Profile_Fragment());
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_about_us:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new About_us_Fragment());
                    fragmentTransaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        bottomNavigationView = findViewById(R.id.nav_view);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        //call first fragment as default
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new HomeFragment());
        fragmentTransaction.commit();


        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

   /* @Override
    protected void onStart() {
        super.onStart();
        if (!SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()){
            Intent intent = new Intent(this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }*/
}
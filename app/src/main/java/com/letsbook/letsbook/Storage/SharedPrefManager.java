package com.letsbook.letsbook.Storage;



import android.content.Context;
import android.content.SharedPreferences;

import com.letsbook.letsbook.Model.GlobalData;
import com.letsbook.letsbook.Model.Model_Login;

public class SharedPrefManager {


    private static final String SHARED_PREF_NAME = "my_shared_preff";

    private static SharedPrefManager mInstance;
    private Context mCtx;

    private SharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;
    }


    public static synchronized SharedPrefManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(mCtx);
        }
        return mInstance;
    }


    public void saveUser(Model_Login user) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("id", user.getId());
        editor.putString("name", user.getName());
        editor.putString("email", user.getEmail());
        editor.putString("password", user.getPassword());
        editor.putString("phone", user.getPhone());
        editor.putString("profile_image", user.getProfile_image());
        editor.putString("status", user.getStatus());
        editor.putString("gender", user.getGender());

        editor.apply();

    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return !sharedPreferences.getString("id", String.valueOf(-1)).equals(String.valueOf(-1));
    }

    public Model_Login getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Model_Login(
                sharedPreferences.getString("id", "-1"),
                sharedPreferences.getString("name", null),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("password", null),
                sharedPreferences.getString("phone", null),
                sharedPreferences.getString("profile_image", null),
                sharedPreferences.getString("status", null),
                sharedPreferences.getString("gender", null)
        );
    }

    public void clear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }




}

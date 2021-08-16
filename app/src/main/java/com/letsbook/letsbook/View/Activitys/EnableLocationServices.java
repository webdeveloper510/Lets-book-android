package com.letsbook.letsbook.View.Activitys;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.letsbook.letsbook.R;
import com.letsbook.letsbook.Storage.SharedPrefManager;

import java.util.List;

import static android.Manifest.permission.READ_PHONE_STATE;

public class EnableLocationServices extends AppCompatActivity {

    private LinearLayout locationButton,signup_login_visibility,notNow,signup,login;
    private ImageView location_image, colour_location_image;
    private TextView title_text, bio_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enable_location_services);

        colour_location_image =findViewById(R.id.location_icon);
        location_image =findViewById(R.id.location_image);
        title_text =findViewById(R.id.enable_location_text);
        bio_text =findViewById(R.id.enable_location_text_bio);
        locationButton =findViewById(R.id.location_button_layout);


        signup_login_visibility =findViewById(R.id.buttons);
        notNow =findViewById(R.id.not_now);
        signup =findViewById(R.id.sign_up);
        login =findViewById(R.id.login);

        notNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnableLocationServices.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnableLocationServices.this, Signup.class);
                startActivity(intent);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnableLocationServices.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void get_location(View view) {
        requestPermissions();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()){
            Intent intent = new Intent(this, Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void requestPermissions() {
        // below line is use to request
        // permission in the current activity.
        Dexter.withContext(this)
                // below line is use to request the number of
                // permissions which are required in our app.
                .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                // after adding permissions we are
                // calling an with listener method.
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        // this method is called when all permissions are granted
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {

                            // do you work now
                            signup_login_visibility.setVisibility(View.VISIBLE);
                            colour_location_image.setVisibility(View.VISIBLE);

                            locationButton.setVisibility(View.GONE);
                            location_image.setVisibility(View.GONE);
                            title_text.setVisibility(View.GONE);
                            bio_text.setVisibility(View.GONE);
                            notNow.setVisibility(View.GONE);
                        }
                        // check for permanent denial of any permission
                        if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permanently,
                            // we will show user a dialog message.
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        // this method is called when user grants some
                        // permission and denies some of them.
                        permissionToken.continuePermissionRequest();
                    }
                }).withErrorListener(new PermissionRequestErrorListener() {
            // this method is use to handle error
            // in runtime permissions
            @Override
            public void onError(DexterError error) {
                // we are displaying a toast message for error message.
                Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
            }
        })
                // below line is use to run the permissions
                // on same thread and to check the permissions
                .onSameThread().check();
    }

    // below is the shoe setting dialog
    // method which is use to display a
    // dialogue message.
    private void showSettingsDialog() {
        // we are displaying an alert dialog for permissions
        AlertDialog.Builder builder = new AlertDialog.Builder(EnableLocationServices.this);

        // below line is the title
        // for our alert dialog.
        builder.setTitle("Need Permissions");

        // below line is our message for our dialog
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // this method is called on click on positive
                // button and on clicking shit button we
                // are redirecting our user from our app to the
                // settings page of our app.
                dialog.cancel();
                // below is the intent from which we
                // are redirecting our user.
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 101);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // this method is called when
                // user click on negative button.
                dialog.cancel();
            }
            // below line is used
        });
        // to display our dialog
        builder.show();
    }


}
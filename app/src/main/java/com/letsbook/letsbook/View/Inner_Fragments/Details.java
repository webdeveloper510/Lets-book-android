package com.letsbook.letsbook.View.Inner_Fragments;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.letsbook.letsbook.Model.GlobalData;
import com.letsbook.letsbook.Model.Model_Login;
import com.letsbook.letsbook.Model.ResponseShopDetails;
import com.letsbook.letsbook.R;
import com.letsbook.letsbook.Storage.SharedPrefManager;
import com.letsbook.letsbook.View.Activitys.Api.ApiClient;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Details extends Fragment implements OnMapReadyCallback {

    LinearLayout openMap, call;
    GoogleMap mMap;
    private View rootView;
    String labelLocation;
    ImageView ownerImage;
    TextView detailsNumber, firstTime, lastTime, morningTime, eveningTime, ownerName, shopAddress;
    private MapView mapView;
    public String number;
    double lat;
    double lng;

    public Details() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_details, container, false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
//                .findFragmentById(R.id.mapview);
//        mapFragment.getMapAsync(this);

//        mapView = rootView.findViewById(R.id.mapview);

        openMap = rootView.findViewById(R.id.map_intent);
        call = rootView.findViewById(R.id.call);
        detailsNumber = rootView.findViewById(R.id.details_number);

        firstTime = rootView.findViewById(R.id.first_time);
        lastTime = rootView.findViewById(R.id.last_time);
        morningTime = rootView.findViewById(R.id.morning_time);
        eveningTime = rootView.findViewById(R.id.evening_time);

        ownerImage = rootView.findViewById(R.id.owner_image);
        ownerName = rootView.findViewById(R.id.owner_name);
        shopAddress = rootView.findViewById(R.id.shop_address);

        openMap.setVisibility(View.GONE);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();

            }
        });

        bookAnAppointment();

        return rootView;
    }


    private void requestPermissions() {
        // below line is use to request
        // permission in the current activity.
        Dexter.withContext(getActivity())
                // below line is use to request the number of
                // permissions which are required in our app.
                .withPermissions(Manifest.permission.CALL_PHONE)
                // after adding permissions we are
                // calling an with listener method.
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        // this method is called when all permissions are granted
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {

                            // do you work now
//                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "9876543210"));
//                            startActivity(intent);

                            Intent callIntent = new Intent(Intent.ACTION_DIAL);
                            callIntent.setData(Uri.parse("tel:" + Uri.encode(number.trim())));
                            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(callIntent);

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
                Toast.makeText(getActivity().getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

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
                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
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

    private void bookAnAppointment() {
        Model_Login user = SharedPrefManager.getInstance(getActivity()).getUser();
        String user_id = user.getId();
        String id = GlobalData.id;

        Call<ResponseShopDetails> call = ApiClient.getInstance().getApi().bookAnAppointment(id,user_id);

        call.enqueue(new Callback<ResponseShopDetails>() {
            @Override
            public void onResponse(@NotNull Call<ResponseShopDetails> call, @NotNull Response<ResponseShopDetails> response) {


                ResponseShopDetails responseShopDetails = (ResponseShopDetails) response.body();
                if (response.code() == 200) {
                    openMap.setVisibility(View.VISIBLE);

                    String latitude = response.body().getData().get(0).getLat();
                    // convert into Double
                    lat = Double.parseDouble(latitude);

                    String longitude = response.body().getData().get(0).getLng();
                    // convert into Double
                    lng = Double.parseDouble(longitude);

                    labelLocation = response.body().getData().get(0).getAddress();

                    openMap.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            String uri = String.format(Locale.ENGLISH, "geo:%f,%f", lat, lng);
//                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri+response.body().getData().get(0).getName()));
//                            getActivity().startActivity(intent);

                            String urlAddress = "http://maps.google.com/maps?q=" + lat + "," + lng + "(" + labelLocation + ")&iwloc=A&hl=es";
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlAddress));
                            startActivity(intent);

                        }
                    });
                    detailsNumber.setText(response.body().getData().get(0).getMobile());
                    number = response.body().getData().get(0).getMobile();
                    firstTime.setText(response.body().getData().get(0).getOpeningTimeMonFri());
                    lastTime.setText(response.body().getData().get(0).getClosingTimeMonFri());
                    morningTime.setText(response.body().getData().get(0).getOpeningTimeSatSun());
                    eveningTime.setText(response.body().getData().get(0).getClosingTimeSatSun());

                    if (response.body().getData().get(0).getShopOwnerImg() != null) {
                        Picasso.get().load(response.body().getData().get(0).getImage()).into(ownerImage);
                    } else {
                        Picasso.get().load(R.drawable.loading).into(ownerImage);
                    }
                    ownerName.setText(response.body().getData().get(0).getShopOwnerName());
                    shopAddress.setText(response.body().getData().get(0).getAddress());

                }
            }

            @Override
            public void onFailure(Call<ResponseShopDetails> call, Throwable t) {


                Log.e("onResponse", t.getMessage() + "");
            }
        });

    }
    // set map view
    public void setLocation(){

//            setMap(lat,lng);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }
}
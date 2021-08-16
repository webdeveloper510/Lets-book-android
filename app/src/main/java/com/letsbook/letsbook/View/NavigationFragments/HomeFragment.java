package com.letsbook.letsbook.View.NavigationFragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.letsbook.letsbook.Adapters.HomeCategoriesAdapter;
import com.letsbook.letsbook.Adapters.HomeBeautyItemsAdapter;
import com.letsbook.letsbook.Adapters.HomeScrollingViewAdapter;
import com.letsbook.letsbook.Adapters.HomeWellnessItemsAdapter;
import com.letsbook.letsbook.Model.GlobalData;
import com.letsbook.letsbook.Model.Model_Login;
import com.letsbook.letsbook.Model.Response_HomeData;
import com.letsbook.letsbook.R;
import com.letsbook.letsbook.Storage.SharedPrefManager;
import com.letsbook.letsbook.View.Activitys.Api.ApiClient;
import com.letsbook.letsbook.View.Inner_Fragments.Beauty;
import com.letsbook.letsbook.View.Inner_Fragments.Wellness;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;


public class HomeFragment extends Fragment {

    public TextView currentLocation;
    public ImageView titleIcon;
    public Toolbar toolbar;
    public LinearLayoutManager linearLayoutManager;
    public HomeScrollingViewAdapter scrollingViewAdapter;
    protected Context context;

    private RecyclerView categoriesRecyclerView, wellnessRecycler, homeHorizontalRecyclerView;
    private ProgressBar homeHorizontalProgressBar;
    private ScrollingPagerIndicator recyclerIndicator;
    private RelativeLayout beauty, fitness;

    private List<Response_HomeData> responseHomeData;
    private HomeCategoriesAdapter homeCategoriesAdapter;
    private FragmentTransaction fragmentTransaction;
    FusedLocationProviderClient fusedLocationProviderClient;


    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        toolbar = view.findViewById(R.id.toolbar);
        currentLocation = view.findViewById(R.id.location);
        homeHorizontalRecyclerView = view.findViewById(R.id.home_horizontal_recyclerview);
        homeHorizontalProgressBar = view.findViewById(R.id.home_horizontal_progressbar);
        recyclerIndicator= view.findViewById(R.id.indicator);
        beauty = view.findViewById(R.id.beauty);
        fitness = view.findViewById(R.id.fitness);
        categoriesRecyclerView = view.findViewById(R.id.home_category_recyclerview);
        wellnessRecycler = view.findViewById(R.id.home_category_wellness_recyclerview);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        requestPermissions();
        beauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new Beauty());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        fitness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new Wellness());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        // API calls
        getSpecialOfTheDay();
        getCategories();
        getWellnessCategories();

        // Special of the day
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        homeHorizontalRecyclerView.setLayoutManager(linearLayoutManager);

//      Item beauty
        LinearLayoutManager itemLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        categoriesRecyclerView.setLayoutManager(itemLayoutManager);

        // Item wellness
        LinearLayoutManager categoriesLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        wellnessRecycler.setLayoutManager(categoriesLayoutManager);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        requestPermissions();
    }

    private void requestPermissions() {
        // below line is use to request
        // permission in the current activity.
        Dexter.withContext(getActivity())
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
                            getMyLocation();
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


    private void getSpecialOfTheDay() {
        Model_Login user = SharedPrefManager.getInstance(getActivity()).getUser();
        String id = user.getId();
        String name = "";
        String categories = "";

        Call<Response_HomeData> call = ApiClient.getInstance().getApi().GetServicesApi(id, name, categories);

        call.enqueue(new Callback<Response_HomeData>() {
            @Override
            public void onResponse(Call<Response_HomeData> call, Response<Response_HomeData> response) {
                homeHorizontalProgressBar.setVisibility(View.GONE);

                Response_HomeData response_homeData = (Response_HomeData) response.body();

                if (response_homeData.getCode() == 200) {

                    if (response_homeData.getData() != null) {
                        List<Response_HomeData.Category> emptyList = new ArrayList<Response_HomeData.Category>();

                        List<Response_HomeData.Datum> list = response_homeData.getData();

                        for (int index = 0; index <= response_homeData.getData().size(); index++) {
                            if (index == 1) {
                                for (int nIndex = 0; nIndex <= list.get(1).getCategories().size() - 1; nIndex++) {
                                    emptyList.add(list.get(2).getCategories().get(nIndex));
                                }
                                break;
                            }
                        }
                        Log.i("onWellnessResponse", emptyList + "");
                        scrollingViewAdapter = new HomeScrollingViewAdapter(emptyList);
                        homeHorizontalRecyclerView.setAdapter(scrollingViewAdapter);
                        recyclerIndicator.attachToRecyclerView(homeHorizontalRecyclerView);

                        LinearSnapHelper snapHelper = new LinearSnapHelper();
                        snapHelper.attachToRecyclerView(homeHorizontalRecyclerView);

                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() < (scrollingViewAdapter.getItemCount() -1)){
                                    linearLayoutManager.smoothScrollToPosition(homeHorizontalRecyclerView,new RecyclerView.State(),
                                            linearLayoutManager.findLastCompletelyVisibleItemPosition()+1);
                                }else{
                                    linearLayoutManager.smoothScrollToPosition(homeHorizontalRecyclerView, new RecyclerView.State(), 0);
                                }

                            }
                        },0,3000);
                    }
                }
            }

            @Override
            public void onFailure(Call<Response_HomeData> call, Throwable t) {
                homeHorizontalProgressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Please check your internet connection !!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getCategories() {
        Model_Login user = SharedPrefManager.getInstance(getActivity()).getUser();
        String id = user.getId();
        String name = "";
        String categories = "";

        Call<Response_HomeData> call = ApiClient.getInstance().getApi().GetServicesApi(id, name, categories);

        call.enqueue(new Callback<Response_HomeData>() {
            @Override
            public void onResponse(Call<Response_HomeData> call, Response<Response_HomeData> response) {

                Response_HomeData response_homeData = (Response_HomeData) response.body();
//                Response_HomeData.Category category = response.body().getData();

                if (response_homeData.getCode() == 200) {

//                    if (response_homeData.getData() != null) {
//                        Log.e("onResponse", response_homeData.getData().+"" );
//                    HomeCategoriesAdapter dataAdapter = new HomeCategoriesAdapter(response_homeData.getData());
//                    categoriesRecyclerView.setAdapter(dataAdapter);
//
//                        HomeItemsAdapter itemsAdapter = new HomeItemsAdapter(response_homeData.getData().);
////                        itemsRecyclerView.setAdapter(itemsAdapter);
//
//
//                    }
                    List<Response_HomeData.Category> emptyList = new ArrayList<Response_HomeData.Category>();

                    List<Response_HomeData.Datum> list = response_homeData.getData();
                    for (int index = 0; index <= response_homeData.getData().size(); index++) {
                        if (index == 0) {
                            for (int nIndex = 0; nIndex <= list.get(0).getCategories().size() - 1; nIndex++) {
                                emptyList.add(list.get(0).getCategories().get(nIndex));
                            }
                            break;
                        }
                    }
                    Log.i("onResponse: ", emptyList + "");
                    HomeBeautyItemsAdapter mDataAdapter = new HomeBeautyItemsAdapter(emptyList);
                    categoriesRecyclerView.setAdapter(mDataAdapter);
                }
            }

            @Override
            public void onFailure(Call<Response_HomeData> call, Throwable t) {

            }
        });
    }

    private void getWellnessCategories() {
        Model_Login user = SharedPrefManager.getInstance(getActivity()).getUser();
        String id = user.getId();
        String name = "";
        String categories = "";

        Call<Response_HomeData> call = ApiClient.getInstance().getApi().GetServicesApi(id, name, categories);

        call.enqueue(new Callback<Response_HomeData>() {
            @Override
            public void onResponse(Call<Response_HomeData> call, Response<Response_HomeData> response) {

                Response_HomeData response_homeData = (Response_HomeData) response.body();
//                Response_HomeData.Category category = response.body().getData();

                if (response_homeData.getCode() == 200) {

//                    if (response_homeData.getData() != null) {
//                        Log.e("onResponse", response_homeData.getData().+"" );
//                    HomeCategoriesAdapter dataAdapter = new HomeCategoriesAdapter(response_homeData.getData());
//                    categoriesRecyclerView.setAdapter(dataAdapter);
//
//                        HomeItemsAdapter itemsAdapter = new HomeItemsAdapter(response_homeData.getData().);
////                        itemsRecyclerView.setAdapter(itemsAdapter);

//                    }
                    List<Response_HomeData.Category> emptyList = new ArrayList<Response_HomeData.Category>();

                    List<Response_HomeData.Datum> list = response_homeData.getData();

                    for (int index = 0; index <= response_homeData.getData().size(); index++) {
                        if (index == 1) {
                            for (int nIndex = 0; nIndex <= list.get(1).getCategories().size() - 1; nIndex++) {
                                emptyList.add(list.get(1).getCategories().get(nIndex));
                            }
                            break;
                        }
                    }
                    Log.i("onWellnessResponse", emptyList + "");
                    HomeWellnessItemsAdapter mDataAdapter = new HomeWellnessItemsAdapter(emptyList);
                    wellnessRecycler.setAdapter(mDataAdapter);
                }
            }

            @Override
            public void onFailure(Call<Response_HomeData> call, Throwable t) {
                Toast.makeText(getActivity(), "Please check your internet connection !!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    void getMyLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager
                .PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            {
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<Location> task) {
                        //Initialize location
                        Location location = task.getResult();
                        if (location != null) {

                            try {
                                Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                                // Initialize address list
                                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                currentLocation.setText(addresses.get(0).getAdminArea());
                                Log.e("getCurrentLocation",  addresses.get(0).getAdminArea());
                                GlobalData.location = addresses.get(0).getAdminArea();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {

                        }
                    }
                });
            }
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } else {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<Location> task) {
                    //Initialize location
                    Location location = task.getResult();
                    if (location != null) {

                        try {
                            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                            // Initialize address list
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            currentLocation.setText(addresses.get(0).getAdminArea());
                            Log.e("getCurrentLocation",  addresses.get(0).getAdminArea());
                            GlobalData.location = addresses.get(0).getAdminArea();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {

                    }
                }
            });
        }

    }
}
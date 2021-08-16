package com.letsbook.letsbook.View.Inner_Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.letsbook.letsbook.Adapters.ShopListAdapter;
import com.letsbook.letsbook.Model.GlobalData;
import com.letsbook.letsbook.Model.ResponseShopsList;
import com.letsbook.letsbook.R;
import com.letsbook.letsbook.View.Activitys.Api.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Wellness extends Fragment {

    private LinearLayout linearLayout;
    private RecyclerView shopListRecyclerView;
    private ProgressBar progressBar;
    private ImageView wellnessBack;
    private TextView wellnessLocation;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    public Wellness() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fitness, container, false);


        wellnessBack = view.findViewById(R.id.wellness_back);
        wellnessLocation = view.findViewById(R.id.wellness_location);
        wellnessLocation.setText(GlobalData.location);
        progressBar = view.findViewById(R.id.fitness_progressbar);
        shopListRecyclerView = view.findViewById(R.id.fitness_recyclerview);

        LinearLayoutManager itemLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        shopListRecyclerView.setLayoutManager(itemLayoutManager);

        wellnessBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(Wellness.this);
                fragmentTransaction.commit();
                fragmentManager.popBackStack();
            }
        });


        getListOfShops();


        return view;
    }
    private void getListOfShops() {
//        Model_Login user = SharedPrefManager.getInstance(getActivity()).getUser();

        double lat = 30.7333;
        double lng = 76.7794;
        int category = 2;
        int distance = 2000;

        Call<ResponseShopsList> call = ApiClient.getInstance().getApi().getShopList(lat, lng, category, distance);

        call.enqueue(new Callback<ResponseShopsList>() {
            @Override
            public void onResponse(Call<ResponseShopsList> call, Response<ResponseShopsList> response) {

                progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {

                    ShopListAdapter dataAdapter = new ShopListAdapter(response.body().getData());
                    shopListRecyclerView.setAdapter(dataAdapter);
                } else {

                    Log.e("responseData", "   response code 201");
                    Toast.makeText(getContext(), "No Data Available !!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseShopsList> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                try {
                    Log.e("responseData", t.toString() + "  try");

                } catch (Exception exception) {
                    Log.e("responseData", exception + "  catch");

                }
                Toast.makeText(getContext(), "No Data Available !!", Toast.LENGTH_SHORT).show();
                Log.e("responseData", t.getMessage());


            }
        });
    }
}
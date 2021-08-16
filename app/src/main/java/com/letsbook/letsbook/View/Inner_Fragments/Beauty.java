package com.letsbook.letsbook.View.Inner_Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class Beauty extends Fragment {


    private ImageView imageView,beautyBack;
    private RecyclerView shopListRecyclerView;
    private TextView beautyLocation;
    private ProgressBar progressBar;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public Beauty() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beauty, container, false);

        beautyLocation = view.findViewById(R.id.beauty_location);
        progressBar = view.findViewById(R.id.progressbar);
        shopListRecyclerView = view.findViewById(R.id.get_shops_details_recyclerview);
        beautyBack = view.findViewById(R.id.beauty_back);

        beautyLocation.setText(GlobalData.location);

        LinearLayoutManager itemLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        shopListRecyclerView.setLayoutManager(itemLayoutManager);

        beautyBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(Beauty.this);
                fragmentTransaction.commit();
                fragmentManager.popBackStack();
                GlobalData.id = "3";
            }
        });

        getListOfShops();

        return view;
    }

    private void getListOfShops() {
//        Model_Login user = SharedPrefManager.getInstance(getActivity()).getUser();

        double lat = 30.7333;
        double lng = 76.7794;
        int category = 1;
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
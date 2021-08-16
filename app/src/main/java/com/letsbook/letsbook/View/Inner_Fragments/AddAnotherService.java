package com.letsbook.letsbook.View.Inner_Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.letsbook.letsbook.Adapters.AddAnotherServiceAdapter;
import com.letsbook.letsbook.Model.GlobalData;
import com.letsbook.letsbook.Model.Model_Login;
import com.letsbook.letsbook.Model.ResponseShopDetails;
import com.letsbook.letsbook.R;
import com.letsbook.letsbook.Storage.SharedPrefManager;
import com.letsbook.letsbook.View.Activitys.Api.ApiClient;
import com.letsbook.letsbook.View.Activitys.Home;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddAnotherService extends Fragment {

    private ProgressBar serviceProgressBar;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private RecyclerView serviceRecyclerView;
    ImageView addAnotherServiceBack;

    public AddAnotherService() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.fragment_add_another_service, container, false);

        serviceProgressBar = view.findViewById(R.id.add_another_service_progressbar);
        serviceRecyclerView = view.findViewById(R.id.add_another_service_recyclerview);
        addAnotherServiceBack = view.findViewById(R.id.add_another_service_back);

        addAnotherServiceBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = ((Home)v.getContext()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new BookAnAppointment());

                fragmentTransaction.commit();
            }
        });

        LinearLayoutManager itemLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        serviceRecyclerView.setLayoutManager(itemLayoutManager);

        bookAnAppointment();

        return view;
    }

    private void bookAnAppointment() {
        Model_Login user = SharedPrefManager.getInstance(getActivity()).getUser();
        String user_id = user.getId();
        String id = GlobalData.id;
        Log.e("values", id );

        Call<ResponseShopDetails> call = ApiClient.getInstance().getApi().bookAnAppointment(id,user_id);

        call.enqueue(new Callback<ResponseShopDetails>() {
            @Override
            public void onResponse(@NotNull Call<ResponseShopDetails> call, @NotNull Response<ResponseShopDetails> response) {
                serviceProgressBar.setVisibility(View.GONE);

                ResponseShopDetails responseShopDetails = (ResponseShopDetails) response.body();
                if (response.code() == 200) {

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
                    List<ResponseShopDetails.ShopCategory> emptyList = new ArrayList<ResponseShopDetails.ShopCategory>();

                    List<ResponseShopDetails.Datum> list = responseShopDetails.getData();
                    for (int index = 0; index <= responseShopDetails.getData().size(); index++) {
                        if (index == 0) {
                            for (int nIndex = 0; nIndex <= list.get(0).getShopCategories().size() - 1; nIndex++) {
                                emptyList.add(list.get(0).getShopCategories().get(nIndex));
                            }
                            break;
                        }
                    }
                    Log.i("listofreviewes", emptyList + "");
                    AddAnotherServiceAdapter serviceAdapter = new AddAnotherServiceAdapter(emptyList);
                    serviceRecyclerView.setAdapter(serviceAdapter);
                }
            }

            @Override
            public void onFailure(Call<ResponseShopDetails> call, Throwable t) {
                serviceProgressBar.setVisibility(View.GONE);

                Log.e("onResponse", t.getMessage() + "");
            }
        });


    }

}
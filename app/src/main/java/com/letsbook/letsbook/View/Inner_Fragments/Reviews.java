package com.letsbook.letsbook.View.Inner_Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.letsbook.letsbook.Adapters.ReviewsAdapter;
import com.letsbook.letsbook.Model.GlobalData;
import com.letsbook.letsbook.Model.Model_Login;
import com.letsbook.letsbook.Model.ResponseShopDetails;
import com.letsbook.letsbook.R;
import com.letsbook.letsbook.Storage.SharedPrefManager;
import com.letsbook.letsbook.View.Activitys.Api.ApiClient;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Reviews extends Fragment {

    private RecyclerView reviewRecyclerView;
    private ProgressBar reviewProgressBar;
    public Reviews() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reviews, container, false);

        reviewRecyclerView = view.findViewById(R.id.review_recyclerview);
        reviewProgressBar = view.findViewById(R.id.review_progressbar);

        reviewProgressBar.setVisibility(View.VISIBLE);
        LinearLayoutManager itemLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        reviewRecyclerView.setLayoutManager(itemLayoutManager);

        bookAnAppointment();

        return view;
    }
    private void bookAnAppointment() {
        Model_Login user = SharedPrefManager.getInstance(getActivity()).getUser();
        String user_id = user.getId();
        String id = GlobalData.id;

        Call<ResponseShopDetails> call = ApiClient.getInstance().getApi().bookAnAppointment(id,user_id);

        call.enqueue(new Callback<ResponseShopDetails>() {
            @Override
            public void onResponse(@NotNull Call<ResponseShopDetails> call, @NotNull Response<ResponseShopDetails> response) {
                reviewProgressBar.setVisibility(View.GONE);

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
                    List<ResponseShopDetails.ReviewDetailsMain> emptyList = new ArrayList<ResponseShopDetails.ReviewDetailsMain>();

                    List<ResponseShopDetails.Datum> list = responseShopDetails.getData();
                    for (int arrayIndex = 0; arrayIndex <= responseShopDetails.getData().size(); arrayIndex++) {
                        if (arrayIndex == 0) {
                            for (int nIndex = 0; nIndex <= list.get(0).getReviewDetailsMain().size() - 1; nIndex++) {
                                emptyList.add(list.get(0).getReviewDetailsMain().get(nIndex));
                            }
                            break;
                        }
                    }
                    Log.i("onResponse", emptyList + "");
                    ReviewsAdapter reviewsAdapter = new ReviewsAdapter(emptyList);
                    reviewRecyclerView.setAdapter(reviewsAdapter);
                }
            }

            @Override
            public void onFailure(Call<ResponseShopDetails> call, Throwable t) {
                reviewProgressBar.setVisibility(View.GONE);

                Log.e("onResponse", t.getMessage() + "");
            }
        });


    }

}
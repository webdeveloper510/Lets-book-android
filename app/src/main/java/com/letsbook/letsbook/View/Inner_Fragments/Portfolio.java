package com.letsbook.letsbook.View.Inner_Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.letsbook.letsbook.Adapters.PortfolioAdapter;
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


public class Portfolio extends Fragment {

    private RecyclerView portfolioRecyclerView;
    private ProgressBar portfolioProgressBar;

    public Portfolio() {}




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_portfolio, container, false);

        portfolioRecyclerView = view.findViewById(R.id.portfolio_recyclerview);
        portfolioProgressBar = view.findViewById(R.id.portfolio_progressbar);

        portfolioProgressBar.setVisibility(View.VISIBLE);

//        LinearLayoutManager itemLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        portfolioRecyclerView.setLayoutManager(itemLayoutManager);

        LinearLayoutManager itemLayoutManager = new GridLayoutManager(getActivity(), 2);
        portfolioRecyclerView.setLayoutManager(itemLayoutManager);


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
                portfolioProgressBar.setVisibility(View.GONE);

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

                    /*List<ResponseShopDetails.Datum> emptyList = new ArrayList<>();

                    List<ResponseShopDetails.Datum> list = responseShopDetails.getData();
                    for (int arrayIndex = 0; arrayIndex <= responseShopDetails.getData().size(); arrayIndex++) {
                        if (arrayIndex == 0) {
                            for (int nIndex = 0; nIndex <= list.get(0).getPortfolioImage().size() - 1; nIndex++) {
                                emptyList.add(list.get(0).getPortfolioImage().get(nIndex));
                            }
                            break;
                        }
                    }
                    Log.i("onResponse", emptyList + "");
                    PortfolioAdapter reviewsAdapter = new PortfolioAdapter(emptyList);
                    portfolioRecyclerView.setAdapter(reviewsAdapter);*/

                    // unused loop for portfolio images
                    Log.i("listofreviewes", responseShopDetails.getData() + "");

                    List<String> portfolioImagesList = new ArrayList<>();
                    List<ResponseShopDetails.Datum> list = responseShopDetails.getData();
                    List<String> portfolioImages =list.get(0).getPortfolioImage();
                    for (int i = 0;  i <= portfolioImages.size()-1; ++i){

                        portfolioImagesList.add(list.get(0).getPortfolioImage().get(i));
                        Log.e("portfolioImages", String.valueOf(portfolioImagesList));
                    }
                    // closed

                    PortfolioAdapter portfolioAdapter = new PortfolioAdapter(portfolioImagesList);
                    portfolioRecyclerView.setAdapter(portfolioAdapter);
                }
            }

            @Override
            public void onFailure(Call<ResponseShopDetails> call, Throwable t) {
                portfolioProgressBar.setVisibility(View.GONE);

                Log.e("onResponse", t.getMessage() + "");
            }
        });
    }
}
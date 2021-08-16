package com.letsbook.letsbook.View.NavigationFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.letsbook.letsbook.Adapters.FavoritesAdapter;
import com.letsbook.letsbook.Adapters.OrderAdapter;
import com.letsbook.letsbook.Model.GetFavorite;
import com.letsbook.letsbook.Model.GlobalData;
import com.letsbook.letsbook.Model.Model_Login;
import com.letsbook.letsbook.Model.MyOrderResponse;
import com.letsbook.letsbook.R;
import com.letsbook.letsbook.Storage.SharedPrefManager;
import com.letsbook.letsbook.View.Activitys.Api.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Favorites extends Fragment {


    private RecyclerView favoritesRecyclerView;
    private ProgressBar favoritesProgressBar;

    public Favorites() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_favorites, container, false);

        favoritesRecyclerView = view.findViewById(R.id.favorites_recyclerview);
        favoritesProgressBar = view.findViewById(R.id.favorites_progressbar);

        LinearLayoutManager itemLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        itemLayoutManager.setReverseLayout(true);
        itemLayoutManager.setStackFromEnd(true);
        favoritesRecyclerView.setLayoutManager(itemLayoutManager);
        getFavoriteShops();

        return view;
    }

    private void getFavoriteShops() {
        Model_Login user = SharedPrefManager.getInstance(getActivity()).getUser();

        String user_id = user.getId();


        Call<GetFavorite> call = ApiClient.getInstance().getApi().getFavourites(user_id);

        call.enqueue(new Callback<GetFavorite>() {
            @Override
            public void onResponse(Call<GetFavorite> call, Response<GetFavorite> response) {

                favoritesProgressBar.setVisibility(View.GONE);

                if (response.code() == 200) {

                    FavoritesAdapter orderAdapter = new FavoritesAdapter(response.body().getData());
                    favoritesRecyclerView.setAdapter(orderAdapter);
                } else {
                    Log.e("responseData", "   response code 201");
                    Toast.makeText(getContext(), "No Data Available !!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetFavorite> call, Throwable t) {
                favoritesProgressBar.setVisibility(View.GONE);

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
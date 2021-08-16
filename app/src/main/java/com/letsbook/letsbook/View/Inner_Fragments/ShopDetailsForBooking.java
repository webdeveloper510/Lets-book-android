package com.letsbook.letsbook.View.Inner_Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.letsbook.letsbook.Adapters.Booking_list_adapter;
import com.letsbook.letsbook.Model.Favourites;
import com.letsbook.letsbook.Model.GlobalData;
import com.letsbook.letsbook.Model.Model_Login;
import com.letsbook.letsbook.Model.ResponseShopDetails;
import com.letsbook.letsbook.R;
import com.letsbook.letsbook.Storage.SharedPrefManager;
import com.letsbook.letsbook.View.Activitys.Api.ApiClient;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShopDetailsForBooking extends Fragment {

    private ImageView booking_list_for_beauty_back, addToFavorite, removeFromFavorite;
    private TextView bookingScreenTitle, bookingScreenBio;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private LinearLayout beautyBannerImageTitleBio;
    ImageView ivPortfolio;


    public ShopDetailsForBooking() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_list_for_beauty, container, false);

        booking_list_for_beauty_back = view.findViewById(R.id.booking_list_for_beauty_back);
        beautyBannerImageTitleBio = view.findViewById(R.id.beauty_banner_image_title_bio);
        ivPortfolio = view.findViewById(R.id.portfolio);
        bookingScreenTitle = view.findViewById(R.id.booking_screen_title);
        bookingScreenBio = view.findViewById(R.id.booking_screen_bio);
        addToFavorite = view.findViewById(R.id.add_to_favorite);
        removeFromFavorite = view.findViewById(R.id.remove_from_favorite);

        //get Api data
        bookAnAppointment();

        // add to favorite
        addToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAddToFavorite();
            }
        });

        // remove from favorite
        removeFromFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRemoveFromFavorite();
            }
        });


        booking_list_for_beauty_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(ShopDetailsForBooking.this);
                fragmentTransaction.commit();
                fragmentManager.popBackStack();
            }
        });

        TabLayout tabLayout = view.findViewById(R.id.bookingListForBeauty_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("SERVICES"));
        tabLayout.addTab(tabLayout.newTab().setText("REVIEWS"));
        tabLayout.addTab(tabLayout.newTab().setText("PORTFOLIO"));
        tabLayout.addTab(tabLayout.newTab().setText("DETAILS"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.bookingListForBeauty_view_pager);
        Booking_list_adapter booking_list_adapter = new Booking_list_adapter(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(booking_list_adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    beautyBannerImageTitleBio.setVisibility(View.VISIBLE);
                } else if (tab.getPosition() == 1) {
                    beautyBannerImageTitleBio.setVisibility(View.GONE);
                } else if (tab.getPosition() == 2) {
                    beautyBannerImageTitleBio.setVisibility(View.GONE);
                } else if (tab.getPosition() == 3) {
                    beautyBannerImageTitleBio.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

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
                if (response.code() == 200) {

                    if (response.body().getData().get(0).getImage() != null) {
                        Picasso.get().load(response.body().getData().get(0).getImage()).into(ivPortfolio);
                    } else {
                        Picasso.get().load(R.drawable.loading).into(ivPortfolio);
                    }
                    bookingScreenTitle.setText(response.body().getData().get(0).getShopName());
                    bookingScreenBio.setText(response.body().getData().get(0).getDescription());
                    int favoriteButtonStatus = response.body().getData().get(0).getStatus();

                    Log.e("favoriteButtonStatus", String.valueOf(favoriteButtonStatus));

                    if(favoriteButtonStatus == 0) {
                        removeFromFavorite.setVisibility(View.VISIBLE);
                        addToFavorite.setVisibility(View.GONE);
                    }

                    if (favoriteButtonStatus == 1) {
                        removeFromFavorite.setVisibility(View.GONE);
                        addToFavorite.setVisibility(View.VISIBLE);
                    }

                }
            }
            @Override
            public void onFailure(Call<ResponseShopDetails> call, Throwable t) {
                Log.e("onResponce", t.getMessage() + "");
            }
        });
    }

    void getAddToFavorite() {
        Model_Login user = SharedPrefManager.getInstance(getActivity()).getUser();

        String user_id = user.getId();
        String id = GlobalData.id;
        int status = 0;

        Call<Favourites> call = ApiClient.getInstance().getApi().addFavourites(user_id, status, id);

        call.enqueue(new Callback<Favourites>() {
            @Override
            public void onResponse(@NotNull Call<Favourites> call, @NotNull Response<Favourites> response) {

                if (response.code() == 200) {

                    Toast.makeText(getActivity(), "Added to favorite !!", Toast.LENGTH_SHORT).show();
                    removeFromFavorite.setVisibility(View.VISIBLE);
                    addToFavorite.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getActivity(), "Something went wrong please try again !!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Favourites> call, Throwable t) {

                Toast.makeText(getActivity(), "Please check your internet connection !!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    void getRemoveFromFavorite() {
        Model_Login user = SharedPrefManager.getInstance(getActivity()).getUser();
        String user_id = user.getId();
        String id = GlobalData.id;
        int status = 1;

        Call<Favourites> call = ApiClient.getInstance().getApi().addFavourites(user_id, status, id);
        call.enqueue(new Callback<Favourites>() {

            @Override
            public void onResponse(@NotNull Call<Favourites> call, @NotNull Response<Favourites> response) {
                if (response.code() == 200) {
                    Toast.makeText(getActivity(), "Removed from favorite !!", Toast.LENGTH_SHORT).show();
                    removeFromFavorite.setVisibility(View.GONE);
                    addToFavorite.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getActivity(), "Something went wrong please try again !!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Favourites> call, Throwable t) {
                Toast.makeText(getActivity(), "Please check your internet connection !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
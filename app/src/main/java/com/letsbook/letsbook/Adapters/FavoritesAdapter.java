package com.letsbook.letsbook.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.letsbook.letsbook.Model.GetFavorite;
import com.letsbook.letsbook.R;
import com.letsbook.letsbook.View.Activitys.Home;
import com.letsbook.letsbook.View.Inner_Fragments.BookAnAppointment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    List<GetFavorite.Datum> favoriteItemList;
    Context context;
    private FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    Fragment fragment;

    public FavoritesAdapter(List<GetFavorite.Datum> favoriteItemList) {
        this.favoriteItemList = favoriteItemList;
    }

    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.favorite_view, viewGroup, false);
        return new FavoritesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoritesAdapter.ViewHolder viewHolder, int i) {

        if (favoriteItemList.get(i).getImage()!=null){
            Picasso.get().load(favoriteItemList.get(i).getImage()).into(viewHolder.favoriteImage);
        }else {
            Picasso.get().load(R.drawable.loading).into(viewHolder.favoriteImage);
        }
        viewHolder.favoriteTitle.setText(favoriteItemList.get(i).getShopName());
        viewHolder.favoriteBio.setText(favoriteItemList.get(i).getBio());
//        viewHolder.favoriteRatingBar.setRating();

//        viewHolder.favoritesView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                fragmentTransaction = ((Home)v.getContext()).getSupportFragmentManager().beginTransaction();
////                fragmentTransaction.replace(R.id.fragment_container, new BookAnAppointment());
////                fragmentTransaction.addToBackStack(null);
////                fragmentTransaction.commit();
//
////                fragment = new BookAnAppointment();
////                fragmentManager = ((Home) v.getContext()).getSupportFragmentManager();
////                fragmentTransaction = fragmentManager.beginTransaction();
////                fragmentTransaction.replace(R.id.fragment_container, fragment);
////                fragmentTransaction.addToBackStack(null);
////                fragmentTransaction.commit();
//
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return favoriteItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView favoriteImage;
        TextView favoriteTitle, favoriteBio;
        RatingBar favoriteRatingBar;
        CardView favoritesView;

        public ViewHolder(View view) {
            super(view);
            favoriteTitle = view.findViewById(R.id.favorite_title);
            favoriteBio = view.findViewById(R.id.favorite_bio);
            favoriteRatingBar = view.findViewById(R.id.favorite_rating);
            favoriteImage = view.findViewById(R.id.favorite_image);
            favoritesView = view.findViewById(R.id.favorite_view);


        }
    }
}

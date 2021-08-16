package com.letsbook.letsbook.Adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.letsbook.letsbook.Model.GlobalData;
import com.letsbook.letsbook.Model.ResponseShopsList;
import com.letsbook.letsbook.R;
import com.letsbook.letsbook.View.Activitys.Home;
import com.letsbook.letsbook.View.Inner_Fragments.ShopDetailsForBooking;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ViewHolder> {



    FragmentTransaction fragmentTransaction;


    List<ResponseShopsList.Datum> shopList;

    public ShopListAdapter(List<ResponseShopsList.Datum> shopList) {
        this.shopList = shopList;
    }

    @Override
    public ShopListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.get_shops_list_view, viewGroup, false);
        return new ShopListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ShopListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.shopListTitle.setText(shopList.get(holder.getAdapterPosition()).getShopName());
        holder.shopListBio.setText(shopList.get(holder.getAdapterPosition()).getDescription());
        holder.shopListStatus.setText(shopList.get(holder.getAdapterPosition()).getClosingTime());
        if (shopList.get(holder.getAdapterPosition()).getImage()!=null){
            Picasso.get().load(shopList.get(holder.getAdapterPosition()).getImage()).into(holder.shopListImage);
        }else {
            Picasso.get().load(R.drawable.loading).resize(200, 200).centerCrop().into(holder.shopListImage);
        }

        holder.viewClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentTransaction = ((Home)v.getContext()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new ShopDetailsForBooking());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                GlobalData.id =  shopList.get(position).getId();
                GlobalData.service_id =  shopList.get(position).getServices();
                Log.e("modelID", holder.id);

            }
        });

    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout viewClick;
        ImageView shopListImage;
        RatingBar shopListRatingBar;
        public String id = "";
        TextView shopListTitle, shopListBio, shopListTime, shopListStatus;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            viewClick = itemView.findViewById(R.id.beauty_more_details);
            shopListImage = itemView.findViewById(R.id.shop_list_image);
            shopListRatingBar = itemView.findViewById(R.id.shop_list_rating);
            shopListTitle = itemView.findViewById(R.id.shop_list_title);
            shopListBio = itemView.findViewById(R.id.shop_list_bio);
            shopListTime = itemView.findViewById(R.id.shop_list_time);
            shopListStatus = itemView.findViewById(R.id.shop_list_status);

        }
    }
}

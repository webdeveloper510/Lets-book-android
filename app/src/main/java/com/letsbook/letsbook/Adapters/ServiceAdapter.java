package com.letsbook.letsbook.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.letsbook.letsbook.Model.ResponseShopDetails;
import com.letsbook.letsbook.R;
import com.letsbook.letsbook.View.Activitys.Home;
import com.letsbook.letsbook.View.Inner_Fragments.BookAnAppointment;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    List<ResponseShopDetails.ShopCategory> shopCategories;
    Context context;
    private FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    Fragment fragment;

    public ServiceAdapter(List<ResponseShopDetails.ShopCategory> beautyLists) {
        this.shopCategories = beautyLists;
    }

    @Override
    public ServiceAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.service_view, viewGroup, false);
        return new ServiceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ServiceAdapter.ViewHolder viewHolder, int i) {

        viewHolder.serviceTitle.setText(shopCategories.get(i).getCategoryName());
        viewHolder.serviceBio.setText(shopCategories.get(i).getDescription());
        viewHolder.servicePrice.setText(shopCategories.get(i).getPrice());
        viewHolder.serviceDuration.setText(shopCategories.get(i).getDuration());
        viewHolder.itemId = shopCategories.get(i).getCategoryId();

        // set values
        viewHolder.categoryName = shopCategories.get(i).getCategoryName();
        viewHolder.getDescription = shopCategories.get(i).getDescription();
        viewHolder.getPrice = shopCategories.get(i).getPrice();
        viewHolder.getDuration = shopCategories.get(i).getDuration();

        viewHolder.serviceBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fragmentTransaction = ((Home)v.getContext()).getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, new BookAnAppointment());
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();

                fragment=new BookAnAppointment();
                fragmentManager=((Home)v.getContext()).getSupportFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                Bundle args = new Bundle();
                args.putString("categoryName", viewHolder.categoryName);
                args.putString("getDescription", viewHolder.getDescription);
                args.putString("getPrice", viewHolder.getPrice);
                args.putString("getDuration", viewHolder.getDuration);
                args.putString("itemId", viewHolder.itemId);
                fragment.setArguments(args);
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();



            }
        });

    }


    @Override
    public int getItemCount() {
        return shopCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        String categoryName,getDescription,getPrice,getDuration,itemId;


        LinearLayout serviceView, serviceBooking;
        private TextView serviceTitle, serviceBio, servicePrice,serviceDuration;
        public ViewHolder(View view) {
            super(view);
            serviceTitle = view.findViewById(R.id.service_title);
            serviceBio = view.findViewById(R.id.service_bio);
            servicePrice = view.findViewById(R.id.service_price);
            serviceDuration = view.findViewById(R.id.service_duration);
            serviceView = view.findViewById(R.id.service_view);
            serviceBooking = view.findViewById(R.id.service_booking);

        }
    }
}

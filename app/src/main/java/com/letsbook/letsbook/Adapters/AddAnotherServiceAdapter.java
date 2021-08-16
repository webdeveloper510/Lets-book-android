package com.letsbook.letsbook.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.letsbook.letsbook.Model.ResponseShopDetails;
import com.letsbook.letsbook.R;
import com.letsbook.letsbook.View.Activitys.Home;
import com.letsbook.letsbook.View.Inner_Fragments.AddAnotherService;
import com.letsbook.letsbook.View.Inner_Fragments.BookAnAppointment;

import java.util.List;

public class AddAnotherServiceAdapter extends RecyclerView.Adapter<AddAnotherServiceAdapter.ViewHolder> {

    List<ResponseShopDetails.ShopCategory> shopCategories;
    Context context;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;

    public AddAnotherServiceAdapter(List<ResponseShopDetails.ShopCategory> beautyLists) {
        this.shopCategories = beautyLists;
    }

    @Override
    public AddAnotherServiceAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.service_view, viewGroup, false);
        return new AddAnotherServiceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddAnotherServiceAdapter.ViewHolder viewHolder, int i) {

        viewHolder.serviceTitle.setText(shopCategories.get(i).getCategoryName());
        viewHolder.serviceBio.setText(shopCategories.get(i).getDescription());
        viewHolder.servicePrice.setText(shopCategories.get(i).getPrice());
        viewHolder.serviceDuration.setText(shopCategories.get(i).getDuration());
        viewHolder.serviceBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = ((Home)v.getContext()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new BookAnAppointment());
                fragmentTransaction.commit();
            }
        });

    }


    @Override
    public int getItemCount() {
        return shopCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

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

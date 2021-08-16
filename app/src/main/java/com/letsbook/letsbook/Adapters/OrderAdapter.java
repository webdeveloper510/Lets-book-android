package com.letsbook.letsbook.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.letsbook.letsbook.Model.MyOrderResponse;
import com.letsbook.letsbook.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {



    List<MyOrderResponse.Datum> orderList;

    public OrderAdapter(List<MyOrderResponse.Datum> orderList) {
        this.orderList = orderList;
    }

    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_order_view, viewGroup, false);
        return new OrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OrderAdapter.ViewHolder holder, int position) {

        holder.orderStatus.setText(orderList.get(position).getStatus());
        holder.orderTitle.setText(orderList.get(position).getItemName());
        holder.orderPrice.setText(orderList.get(position).getAmount());
        holder.orderDate.setText(orderList.get(position).getDate());
        holder.orderTime.setText(orderList.get(position).getTime());
        holder.orderBio.setText(orderList.get(position).getItemBio());
        holder.orderAddress.setText(orderList.get(position).getShopAddress());



        /*holder.viewClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentTransaction = ((Home)v.getContext()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new BookingListForBeauty());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                ModalData.id =  orderList.get(position).getId();
                ModalData.service_id =  orderList.get(position).getServices();
                Log.e("modelID", holder.id);

            }
        });*/

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout viewClick;
        ImageView shopListImage;
        RatingBar shopListRatingBar;
        String time;
        TextView orderTitle, orderStatus, orderPrice, orderDate, orderTime,orderAddress, orderBio;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            orderTitle = itemView.findViewById(R.id.order_title);
            orderStatus = itemView.findViewById(R.id.order_status);
            orderPrice = itemView.findViewById(R.id.order_price);
            orderDate = itemView.findViewById(R.id.order_date);
            orderTime = itemView.findViewById(R.id.order_time);
            orderBio = itemView.findViewById(R.id.order_bio);
            orderAddress = itemView.findViewById(R.id.order_address);

        }
    }
}

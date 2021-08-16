package com.letsbook.letsbook.View.NavigationFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.letsbook.letsbook.Adapters.OrderAdapter;
import com.letsbook.letsbook.Adapters.ShopListAdapter;
import com.letsbook.letsbook.Model.Model_Login;
import com.letsbook.letsbook.Model.MyOrderResponse;
import com.letsbook.letsbook.Model.ResponseShopsList;
import com.letsbook.letsbook.R;
import com.letsbook.letsbook.Storage.SharedPrefManager;
import com.letsbook.letsbook.View.Activitys.Api.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class My_order_Fragment extends Fragment {

    private RecyclerView myOrderRecyclerView;
    private ProgressBar myOrderProgressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_order_, container, false);

        myOrderRecyclerView = view.findViewById(R.id.my_order_recyclerview);
        myOrderProgressBar = view.findViewById(R.id.my_order_progressbar);

        LinearLayoutManager itemLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        itemLayoutManager.setReverseLayout(true);
        itemLayoutManager.setStackFromEnd(true);
        myOrderRecyclerView.setLayoutManager(itemLayoutManager);

        getMyOrder();
        return view;
    }

    private void getMyOrder() {
        Model_Login user = SharedPrefManager.getInstance(getActivity()).getUser();

        String user_id = user.getId();

        Call<MyOrderResponse> call = ApiClient.getInstance().getApi().myOrder(user_id);

        call.enqueue(new Callback<MyOrderResponse>() {
            @Override
            public void onResponse(Call<MyOrderResponse> call, Response<MyOrderResponse> response) {

                myOrderProgressBar.setVisibility(View.GONE);

                if (response.code() == 200) {

                    OrderAdapter orderAdapter = new OrderAdapter(response.body().getData());
                    myOrderRecyclerView.setAdapter(orderAdapter);
                } else {
                    Log.e("responseData", "   response code 201");
                    Toast.makeText(getContext(), "No Data Available !!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MyOrderResponse> call, Throwable t) {
                myOrderProgressBar.setVisibility(View.GONE);

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
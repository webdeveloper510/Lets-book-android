package com.letsbook.letsbook.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.letsbook.letsbook.Model.Response_HomeData;
import com.letsbook.letsbook.R;
import com.letsbook.letsbook.View.Activitys.Home;
import com.letsbook.letsbook.View.Inner_Fragments.Wellness;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeScrollingViewAdapter extends RecyclerView.Adapter<HomeScrollingViewAdapter.ViewHolder> {

    private FragmentTransaction fragmentTransaction;
    List<Response_HomeData.Category> itemsList;
    Context context;


    public HomeScrollingViewAdapter(List<Response_HomeData.Category> beautyLists) {
        this.itemsList = beautyLists;
    }

    @Override
    public HomeScrollingViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.special_of_the_day_view, viewGroup, false);

        return new HomeScrollingViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeScrollingViewAdapter.ViewHolder viewHolder, int i) {


        if (itemsList.get(i).getImage() != null) {
            Picasso.get().load(itemsList.get(i).getImage()).into(viewHolder.scrollingViewImage);

        } else {
            Picasso.get().load(R.drawable.loading).into(viewHolder.scrollingViewImage);

        }


       /* viewHolder.items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = ((Home)v.getContext()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new Wellness());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });*/

    }


    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView scrollingViewImage;

        public ViewHolder(View view) {
            super(view);
            scrollingViewImage = view.findViewById(R.id.scrolling_view_image);


        }
    }
}

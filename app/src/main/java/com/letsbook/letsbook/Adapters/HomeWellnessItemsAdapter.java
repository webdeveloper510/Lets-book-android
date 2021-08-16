package com.letsbook.letsbook.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.letsbook.letsbook.Model.Response_HomeData;
import com.letsbook.letsbook.R;
import com.letsbook.letsbook.View.Activitys.Home;
import com.letsbook.letsbook.View.Inner_Fragments.Wellness;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeWellnessItemsAdapter extends RecyclerView.Adapter<HomeWellnessItemsAdapter.ViewHolder>{

    private FragmentTransaction fragmentTransaction;
    List<Response_HomeData.Category> itemsList;
    Context context;


    public HomeWellnessItemsAdapter(List<Response_HomeData.Category> beautyLists) {
        this.itemsList = beautyLists;
    }

    @Override
    public HomeWellnessItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items_view, viewGroup, false);

        return new HomeWellnessItemsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeWellnessItemsAdapter.ViewHolder viewHolder, int i) {

//        Log.e("categories", (itemsList.get(i).getName() +"  categories are here"));
//        Log.e("categories", String.valueOf(itemsList.get(i).getCategories().get(0).getName()) +"  categories are here");
//        Log.e("categories", String.valueOf(itemsList.get(i).getName()) +"  categories are here");

        viewHolder.itemTitle.setText(itemsList.get(i).getName());
        if (itemsList.get(i).getImage()!=null){
            Picasso.get().load(itemsList.get(i).getImage()).into(viewHolder.itemImage);

        }else {
            Picasso.get().load(R.drawable.loading).into(viewHolder.itemImage);

        }
        viewHolder.itemTitle.setText(itemsList.get(i).getName());

        viewHolder.items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                fragmentTransaction = ((Home)v.getContext()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new Wellness());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }


    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout items;
        private ImageView itemImage;
        private TextView itemTitle;
        public ViewHolder(View view) {
            super(view);
            itemImage = view.findViewById(R.id.item_image);
            itemTitle = view.findViewById(R.id.item_title);
            items = view.findViewById(R.id.items_layout);

        }
    }
}

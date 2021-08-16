package com.letsbook.letsbook.Adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.letsbook.letsbook.Model.ResponseShopDetails;
import com.letsbook.letsbook.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PortfolioAdapter  extends RecyclerView.Adapter<PortfolioAdapter.ViewHolder> {

    List<String> portfolioImages;
    Context context;

    public PortfolioAdapter(List<String> portfolioImages) {
        this.portfolioImages = portfolioImages;
    }



    @Override
    public PortfolioAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.portfolio_view, viewGroup, false);
        return new PortfolioAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PortfolioAdapter.ViewHolder viewHolder, int i) {

      //  viewHolder.urls = portfolioImages.get(i);

        if (portfolioImages.get(i)!=null){
            Picasso.get().load(portfolioImages.get(i)).into(viewHolder.portfolioImages);
        }else {
            Picasso.get().load(R.drawable.loading).into(viewHolder.portfolioImages);
        }


//        viewHolder.serviceBooking.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "shopCategories.get(i).getCategoryName()", Toast.LENGTH_SHORT).show();
//            }
//        });

    }


    @Override
    public int getItemCount() {
        return portfolioImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView portfolioImages;
        List<String> urls;

        private TextView reviewTitle, reviewBio;
        public ViewHolder(View view) {
            super(view);
            portfolioImages = view.findViewById(R.id.portfolio_images);
        }
    }
}

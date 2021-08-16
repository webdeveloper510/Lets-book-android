package com.letsbook.letsbook.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.letsbook.letsbook.Model.ResponseShopDetails;
import com.letsbook.letsbook.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReviewsAdapter  extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    List<ResponseShopDetails.ReviewDetailsMain> reviewDetails;
    Context context;

    public ReviewsAdapter(List<ResponseShopDetails.ReviewDetailsMain> reviewDetailsMains) {
        this.reviewDetails = reviewDetailsMains;
    }

    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_view, viewGroup, false);
        return new ReviewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewsAdapter.ViewHolder viewHolder, int i) {

        if (reviewDetails.get(i).getBio()!=null){
            Picasso.get().load(reviewDetails.get(i).getBio()).into(viewHolder.reviewUserProfile);
        }else {
            Picasso.get().load(R.drawable.loading).into(viewHolder.reviewUserProfile);
        }
        viewHolder.reviewTitle.setText(reviewDetails.get(i).getReviewTitle());
        viewHolder.reviewBio.setText(reviewDetails.get(i).getReviewDesc());

        String stringRating = reviewDetails.get(i).getRating();
        float floatRating =Float.parseFloat(stringRating);
        viewHolder.reviewRating.setRating(floatRating);

//        viewHolder.serviceBooking.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "shopCategories.get(i).getCategoryName()", Toast.LENGTH_SHORT).show();
//            }
//        });

    }


    @Override
    public int getItemCount() {
        return reviewDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout  serviceBooking;
        ImageView reviewUserProfile;
        RatingBar reviewRating;
        private TextView reviewTitle, reviewBio;
        public ViewHolder(View view) {
            super(view);
            reviewUserProfile = view.findViewById(R.id.review_user_profile);
            reviewTitle = view.findViewById(R.id.review_title);
            reviewBio = view.findViewById(R.id.review_bio);
            reviewRating = view.findViewById(R.id.review_rating);


        }
    }
}

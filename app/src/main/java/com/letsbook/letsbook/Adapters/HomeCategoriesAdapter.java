package com.letsbook.letsbook.Adapters;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.letsbook.letsbook.Model.Response_HomeData;
import com.letsbook.letsbook.R;
import java.util.List;

public class HomeCategoriesAdapter extends RecyclerView.Adapter<HomeCategoriesAdapter.ViewHolder> {

    List<Response_HomeData.Datum> beautyLists;

    public HomeCategoriesAdapter(List<Response_HomeData.Datum> beautyLists) {
        this.beautyLists = beautyLists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.categery_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.customViewTitle.setText(beautyLists.get(i).getName());
        Log.e("categories", String.valueOf(beautyLists.get(i).getCategories().get(0).getName()) +"  categories are here");
    }

    @Override
    public int getItemCount() {
        return beautyLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView customViewTitle;

        public ViewHolder(View view) {
            super(view);
            customViewTitle = view.findViewById(R.id.custom_view_title);

        }
    }
}

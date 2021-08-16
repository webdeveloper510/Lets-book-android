package com.letsbook.letsbook.Adapters;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.letsbook.letsbook.Model.GlobalData;
import com.letsbook.letsbook.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder> {

    Context context;
    List<String> getTimeSlots;

    public TimeAdapter(List<String> getTimeSlots) {
        this.getTimeSlots = getTimeSlots;
    }

    @Override
    public TimeAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.time_view, viewGroup, false);
        return new TimeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TimeAdapter.ViewHolder holder, int position) {

        holder.timeSlot.setText(getTimeSlots.get(holder.getAdapterPosition()));


        holder.timeSlotView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.timeSlotView.isSelected()) {
                    Toast.makeText(v.getContext(), holder.timeSlotView.getId() + "", Toast.LENGTH_SHORT).show();
                }

                GlobalData.selectedTime = getTimeSlots.get(holder.getAdapterPosition());
                holder.timeSlotView.setBackgroundResource(R.drawable.selected_time_border);
                holder.timeSlot.setTextColor(Color.WHITE);
                Log.e("modelValues", GlobalData.selectedTime);

            }
        });

    }

    @Override
    public int getItemCount() {
        return getTimeSlots.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout timeSlotView;
        TextView timeSlot;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            timeSlotView = itemView.findViewById(R.id.time_slot_view);
            timeSlot = itemView.findViewById(R.id.time_slot);


        }
    }
}

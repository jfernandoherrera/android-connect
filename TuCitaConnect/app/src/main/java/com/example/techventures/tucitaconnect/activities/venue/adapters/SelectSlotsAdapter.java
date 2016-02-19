package com.example.techventures.tucitaconnect.activities.venue.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.techventures.tucitaconnect.R;

import java.util.Calendar;

public class SelectSlotsAdapter extends RecyclerView.Adapter<SelectSlotsAdapter.ViewHolder> {

    int duration;
    int amount;
    Calendar initialDate;

    public SelectSlotsAdapter(int duration, int amount, Calendar initialDate){

        this.duration = duration;

        this.amount = amount;

        this.initialDate = initialDate;

    }

    public void setAmount(int amount) {

        this.amount = amount;

        notifyDataSetChanged();

    }

    public void setInitialDate(Calendar initialDate) {

        this.initialDate = initialDate;

        notifyDataSetChanged();

    }

    @Override
    public SelectSlotsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_slot, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SelectSlotsAdapter.ViewHolder holder, int position) {

        if(holder.selected) {

            holder.textView.setBackgroundResource(R.drawable.border_selected_slot);

        } else {

            holder.textView.setBackgroundResource(R.drawable.border_left_bar);

        }



        holder.textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(holder.selected){

                    holder.selected = false;

                    holder.textView.setBackgroundResource(R.drawable.border_left_bar);

                }else {

                    holder.selected = true;

                    holder.textView.setBackgroundResource(R.drawable.border_selected_slot);

                }

            }
        });

        holder.textView.setText(hour(position));

    }

    private String hour(int position){

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, initialDate.get(Calendar.HOUR_OF_DAY));

        calendar.set(Calendar.MINUTE, initialDate.get(Calendar.MINUTE));

        int time = position * duration;

        calendar.add(Calendar.MINUTE, time);

        String hourSlot = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);

        return hourSlot;

    }

    @Override
    public int getItemCount() {

        return amount;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        boolean selected;

        public ViewHolder(final View itemView){

            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.lapses);

            selected = false;

        }
    }
}

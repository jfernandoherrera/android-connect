package com.example.techventures.tucitaconnect.activities.venue.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.techventures.tucitaconnect.R;

import java.util.Calendar;


public class LeftBarAdapter extends RecyclerView.Adapter<LeftBarAdapter.ViewHolder> {

    int duration;
    int amount;
    Calendar initialDate;

    public LeftBarAdapter(int duration, int amount, Calendar initialDate){

        this.duration = duration;

        this.amount = amount;

        this.initialDate = initialDate;

    }

    public void setAmount(int amount) {

        this.amount = amount;

        notifyDataSetChanged();

    }


    private String formatHour(int hour, int minute) {

        int twelveHoursClock = 12;

        String fine;

        String am = "AM";

        String pm = "PM";

        String ampm;

        if(hour > twelveHoursClock) {

            hour = hour - twelveHoursClock;

            ampm = pm;

        }else {

            ampm = am;

        }

        String min;

        if(minute < 10) {

            min = "0" + minute;

        }else {

            min = String.valueOf(minute);

        }

        fine = hour + ":" + min + "  " + ampm;

        return fine;

    }

    public void setInitialDate(Calendar initialDate) {

        this.initialDate = initialDate;

        notifyDataSetChanged();

    }

    @Override
    public LeftBarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_lapsus, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LeftBarAdapter.ViewHolder holder, int position) {

        holder.textView.setBackgroundResource(R.drawable.border_left_bar);

        holder.textView.setText(hour(position));

    }

    private String hour(int position){

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, initialDate.get(Calendar.HOUR_OF_DAY));

        calendar.set(Calendar.MINUTE, initialDate.get(Calendar.MINUTE));

        int time = position * duration;

        calendar.add(Calendar.MINUTE, time);

        String hourSlot = formatHour(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));

        return hourSlot;

    }

    @Override
    public int getItemCount() {

        return amount;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(final View itemView){

            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.lapses);

        }
    }
}

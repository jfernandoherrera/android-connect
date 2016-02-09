package com.example.techventures.tucitaconnect.activities.venue.adapters;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.techventures.tucitaconnect.R;
import com.example.techventures.tucitaconnect.model.domain.slot.Slot;
import com.example.techventures.tucitaconnect.utils.common.ViewUtils;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;


public class SlotsAppointmentsAdapter  extends RecyclerView.Adapter<SlotsAppointmentsAdapter.ViewHolder> {

        private List<Slot> items;
        private Typeface typeface;
        private int amount;

        public SlotsAppointmentsAdapter(List<Slot> offer, Typeface typeface, int columns) {

            super();

            this.typeface = typeface;

            items = offer;

            amount = columns;

        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_slot, viewGroup, false);

            ViewHolder viewHolder = new ViewHolder(v);

            return viewHolder;

        }

    public void setAmount(int amount) {

        this.amount = amount;

    }

    @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {

            Slot slot = items.get(i / amount);

            viewHolder.appointment.setText( slot.getFormattedHour());

            int residue = i % amount;

            if(residue < slot.getAmount()){


                viewHolder.appointment.setBackgroundResource(R.drawable.border);

            }else {

                blocked(viewHolder);

                viewHolder.appointment.setText("");

            }
        }

        private void confirmed(ViewHolder viewHolder){

            viewHolder.appointment.setBackgroundColor(Color.GREEN);

        }

        private void unconfirmed(ViewHolder viewHolder){

            viewHolder.appointment.setBackgroundColor(Color.RED);

        }

        private void free(ViewHolder viewHolder) {

            viewHolder.appointment.setBackgroundColor(Color.LTGRAY);

        }

        private void blocked(ViewHolder viewHolder){

            viewHolder.appointment.setBackgroundColor(Color.GRAY);

        }

        @Override
        public int getItemCount() {

            return items.size() * amount;

        }

        class ViewHolder extends RecyclerView.ViewHolder {

            protected TextView appointment;

            public ViewHolder(final View itemView) {

                super(itemView);

                appointment = (TextView) itemView.findViewById(R.id.appointment);

                appointment.setTypeface(typeface, Typeface.BOLD);

                itemView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        v.callOnClick();

                        return true;
                    }
                });

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.i("hoja", "papel");
                    }
                });

            }

        }

}

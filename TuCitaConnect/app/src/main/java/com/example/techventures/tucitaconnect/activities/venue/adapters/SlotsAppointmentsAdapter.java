package com.example.techventures.tucitaconnect.activities.venue.adapters;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.techventures.tucitaconnect.R;
import com.example.techventures.tucitaconnect.model.domain.appointment.Appointment;
import com.example.techventures.tucitaconnect.model.domain.slot.Slot;

import java.util.Date;
import java.util.List;


public class SlotsAppointmentsAdapter  extends RecyclerView.Adapter<SlotsAppointmentsAdapter.ViewHolder> {

        private List<Slot> slots;
        private Typeface typeface;
        private List<Appointment> appointments;
        private int amount;


        public interface OnTouchToClick{

            void onTouchToClick(int x, int y, Appointment appointment);

        }

        public SlotsAppointmentsAdapter(List<Slot> offer, Typeface typeface, int columns, List<Appointment> appointments) {

            super();

            this.typeface = typeface;

            slots = offer;

            amount = columns;

            this.appointments = appointments;

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

            Slot slot = slots.get(i / amount);

            viewHolder.textViewAppointment.setText(slot.getFormattedHour());

            int residue = i % amount;

            if(residue < slot.getAmount()){

                free(viewHolder);

            }else {

                blocked(viewHolder);

                viewHolder.textViewAppointment.setText("");

            }

            Appointment appointment = getAppointment(slot);

            if(appointment != null){

                viewHolder.appointment = appointment;

                unconfirmed(viewHolder);

            }

        }

    private int[] sixtyMinutes(int hour, int minutes) {

        int[] time = new int[2];

        if (minutes < 60) {

            time[0] = hour;

            time[1] = minutes;

        } else {

            time[0] = hour + 1;

            minutes = minutes - 60;

            time[1] = minutes;

        }

        return time;

    }

    private Appointment getAppointment(Slot slot){

        Appointment appointmentSlot = null;

        for (Appointment appointment : appointments) {

            Date appointmentDate = appointment.getDate();

            int startHour = appointmentDate.getHours();

            int startMinute = appointmentDate.getMinutes();

            int[] duration = appointment.getDuration();

            int[] endTime = sixtyMinutes(startHour + duration[0], startMinute + duration[1]);

                boolean isFirst = slot.isSmallerOrEqual(startHour, startMinute) && slot.endIsGreater(startHour, startMinute);

                boolean contained = slot.isGreater(startHour, startMinute) && slot.endIsSmaller(endTime[0], endTime[1]);

                boolean isLast = slot.isSmaller(endTime[0], endTime[1]) && slot.endIsGreaterOrEqual(endTime[0], endTime[1]);

                if (isFirst || contained || isLast) {

                    if(! slot.isIn(appointment)) {

                        appointmentSlot = appointment;

                        slot.addAppointment(appointment);
                        
                        if (appointment.isSloted()) {

                            appointments.remove(appointment);

                        } else {

                            appointment.incrementTimeSlot(slot.getDurationMinutes());

                        }

                    break;

                    }

                }

        }

        return appointmentSlot;

    }

        private void confirmed(ViewHolder viewHolder){

            viewHolder.textViewAppointment.setBackgroundColor(Color.GREEN);

        }

        private void unconfirmed(ViewHolder viewHolder){

            viewHolder.textViewAppointment.setBackgroundResource(R.drawable.border_uncorfimed);

        }

        private void free(ViewHolder viewHolder) {

            viewHolder.textViewAppointment.setBackgroundResource(R.drawable.border_free);

        }

        private void blocked(ViewHolder viewHolder){

            viewHolder.textViewAppointment.setBackgroundColor(Color.GRAY);

        }

        @Override
        public int getItemCount() {

            return slots.size() * amount;

        }

        class ViewHolder extends RecyclerView.ViewHolder {

            protected TextView textViewAppointment;
            protected Appointment appointment;

            public ViewHolder(final View itemView) {

                super(itemView);

                textViewAppointment = (TextView) itemView.findViewById(R.id.appointment);

                textViewAppointment.setTypeface(typeface, Typeface.BOLD);

                itemView.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        Log.i("hoja " + event.getAction(), "papel " + MotionEvent.ACTION_UP +" "+ MotionEvent.ACTION_DOWN );

                        return false;
                    }
                });


            }

        }

}

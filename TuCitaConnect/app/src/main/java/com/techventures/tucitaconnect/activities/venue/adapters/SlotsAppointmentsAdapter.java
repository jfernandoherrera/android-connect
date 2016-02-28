package com.techventures.tucitaconnect.activities.venue.adapters;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techventures.tucitaconnect.R;
import com.techventures.tucitaconnect.model.domain.appointment.Appointment;
import com.techventures.tucitaconnect.model.domain.slot.Slot;

import java.util.Date;
import java.util.List;


public class SlotsAppointmentsAdapter  extends RecyclerView.Adapter<SlotsAppointmentsAdapter.ViewHolder> {

        private List<Slot> slots;
        private Typeface typeface;
        private List<Appointment> appointments;
        private int amount;
        private int column;
        private OnTouchToClick listener;


        public interface OnTouchToClick{

            void onTouchToClick(int x, int y, Slot slot, int column);

        }

        public SlotsAppointmentsAdapter(List<Slot> offer, Typeface typeface, int columns, List<Appointment> appointments, OnTouchToClick onTouchToClick) {

            super();

            this.typeface = typeface;

            slots = offer;

            amount = columns;

            this.appointments = appointments;

            column = 0;

            listener = onTouchToClick;

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

    public void setAppointments(List<Appointment> appointmentList){

        appointments.clear();

        appointments.addAll(appointmentList);

        for(Appointment appointment : appointmentList) {

            appointment.restart();

        }

        notifyDataSetChanged();

    }

    public void setSlots(List<Slot> slotList){

        slots.clear();

        slots.addAll(slotList);

        for(Slot slot : slots){

            slot.restart();

        }

        notifyDataSetChanged();

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        int index = i / amount;

        Slot slot = slots.get(index);

        viewHolder.slot = slot;

            int residue = i % amount;

            if(residue < slot.getAmount()){

                free(viewHolder);

            }else {

                blocked(viewHolder);

                viewHolder.textViewAppointment.setText("");

            }

            Appointment appointment = getAppointment(slot, viewHolder);

            if(appointment != null && appointment.getColumn() == column){

                viewHolder.slot = slot;

            }

            viewHolder.column = column;

            column ++;

        if(column == amount ){

            column = 0;
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

    private Appointment getAppointment(Slot slot, ViewHolder viewHolder){

        Appointment appointmentSlot = null;

        int noAssigned = -1;

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

                    if(appointment.getColumn() == noAssigned) {

                        appointment.setColumn(column);

                    }

                    if( appointment.getColumn() == column) {

                        appointmentSlot = appointment;

                        slot.addAppointment(appointment);

                        if (isLast) {

                            appointments.remove(appointment);

                            unconfirmedLast(viewHolder);

                            break;

                        }

                        if (contained) {

                            unconfirmed(viewHolder);

                            break;

                        }

                            unconfirmedFirst(viewHolder);

                            String text = appointment.getUser().getName();

                            viewHolder.textViewAppointment.setText(text);

                            break;

                    }

                }

        }

        return appointmentSlot;

    }

        private void confirmed(ViewHolder viewHolder){

            viewHolder.textViewAppointment.setBackgroundColor(Color.GREEN);

        }

        private void unconfirmedLast(ViewHolder viewHolder){

            viewHolder.textViewAppointment.setBackgroundResource(R.drawable.border_unconfirmed_last);

        }

    private void unconfirmedFirst(ViewHolder viewHolder){

        viewHolder.textViewAppointment.setBackgroundResource(R.drawable.border_unconfirmed_first);

    }

    private void unconfirmed(ViewHolder viewHolder){

        viewHolder.textViewAppointment.setBackgroundResource(R.drawable.border_uncorfimed_contained);

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
            protected Slot slot;
            protected int column;

            public ViewHolder(final View itemView) {

                super(itemView);

                textViewAppointment = (TextView) itemView.findViewById(R.id.appointment);

                textViewAppointment.setTypeface(typeface, Typeface.BOLD);

                itemView.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        listener.onTouchToClick((int) event.getX(), (int) event.getY(), slot, column);

                        return false;
                    }
                });


            }

        }

}

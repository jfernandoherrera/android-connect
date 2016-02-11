package com.example.techventures.tucitaconnect.model.domain.slot;

import com.example.techventures.tucitaconnect.model.domain.appointment.Appointment;
import com.example.techventures.tucitaconnect.utils.common.ViewUtils;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@ParseClassName("Slot")
public class Slot extends ParseObject {

    private int amount;
    private Calendar date;
    private List<Appointment> appointments;

    public boolean isIn(Appointment appointment){

        boolean isIn;

        if(appointments == null){

            appointments  = new ArrayList<>();

            isIn = false;

        }else {

           isIn = appointments.contains(appointment);

        }

        return isIn;

    }

    public void restart(){

        appointments  = new ArrayList<>();

    }

    public void addAppointment(Appointment appointment){

        appointments.add(appointment);

    }

    public int getDay() {

        return getInt(SlotAttributes.day);

    }

    public Calendar getDate() {

        return date;

    }

    public void setDate(Calendar date) {

        this.date = date;

    }

    public int getDurationMinutes(){

        return getInt(SlotAttributes.durationMinutes);

    }

    public int[] getDuration() {

        int[] duration = new int[2];

        int durationHours = 0;

        int durationMinutes = getInt(SlotAttributes.durationMinutes);

        if (durationMinutes >= 60) {

            durationHours = durationMinutes / 60;

            durationMinutes = durationMinutes - (60 * durationHours);

        }

        duration[0] = durationHours;

        duration[1] = durationMinutes;

        return duration;

    }

    public int getStartMinute() {

        return getInt(SlotAttributes.startMinute);

    }

    public void setAmount() {

        amount = getInt(SlotAttributes.amount);

    }

    public void decrementAmount() {

        amount--;

    }

    public int getAmount() {

        return amount;

    }

    public int getStartHour() {

        return getInt(SlotAttributes.startHour);

    }

    public int getEndHour() {

        int endHour = getStartHour();

        if (getStartMinute() + getDuration()[1] >= 60) {

            endHour++;

        }

        endHour += getDuration()[0];

        return endHour;

    }

    public String getFormattedHour() {

        ViewUtils viewUtils = new ViewUtils(null);

        return viewUtils.hourFormat(getStartHour(), getStartMinute());

    }

    public int getEndMinute() {

        int minute;

        int incremented = getStartMinute() + getDuration()[1];

        if (incremented >= 60) {

            minute = incremented - 60;

        } else {

            minute = incremented;

        }

        return minute;

    }

    public boolean isGreater(int hour, int minute) {

        boolean isGreater = (hour < getStartHour()) || ((hour == getStartHour()) && (minute < getStartMinute()));

        return isGreater;

    }

    public boolean endIsGreater(int hour, int minute) {

        boolean isGreater = (hour < getEndHour() || ((hour == getEndHour()) && (minute < getEndMinute())));

        return isGreater;

    }

    public boolean endIsGreaterOrEqual(int hour, int minute) {

        boolean isGreaterOrEqual = (hour < getEndHour()) || ((hour == getEndHour()) && (minute <= getEndMinute()));

        return isGreaterOrEqual;

    }

    public boolean isSmaller(int hour, int minute) {

        boolean isSmaller = (hour > getStartHour()) || ((hour == getStartHour()) && (minute > getStartMinute()));

        return isSmaller;

    }

    public boolean endIsSmaller(int hour, int minute) {

        boolean isSmaller = (hour > getEndHour()) || ((hour == getEndHour()) && (minute > getEndMinute()));

        return isSmaller;

    }

    public boolean isSmallerOrEqual(int hour, int minute) {

        boolean isSmallerOrEqual = (hour > getStartHour()) || ((hour == getStartHour()) && (minute >= getStartMinute()));

        return isSmallerOrEqual;

    }

    public static ParseQuery<Slot> getQuery() {

        return ParseQuery.getQuery(Slot.class);

    }

}

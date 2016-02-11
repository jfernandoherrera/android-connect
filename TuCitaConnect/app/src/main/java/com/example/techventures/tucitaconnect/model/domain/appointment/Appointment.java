package com.example.techventures.tucitaconnect.model.domain.appointment;

import com.example.techventures.tucitaconnect.model.domain.user.User;
import com.example.techventures.tucitaconnect.model.domain.venue.Venue;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.util.Calendar;
import java.util.Date;

@ParseClassName("Appointment")
public class Appointment extends ParseObject {

    int timeSlotHour = 0;

    int timeSlotMinute = 0;

    int column = -1;

    public int getColumn() {

        return column;

    }

    public void setColumn(int column) {

        this.column = column;

    }

    public int getTimeSlotHour() {

        return timeSlotHour;

    }


    public int getTimeSlotMinute() {

        return timeSlotMinute;

    }

    public void restart(){

        int timeSlotHour = 0;

        int timeSlotMinute = 0;

    }

    public boolean isSloted(){

        int[ ] duration = getDuration();

        boolean isSloted = timeSlotHour == duration[0] && timeSlotMinute == duration[1];

        return isSloted;
    }

    public void incrementTimeSlot(int incrementMinutes){

        int durationMinutes = incrementMinutes + timeSlotMinute;

        if( durationMinutes >= 60) {

            timeSlotHour = durationMinutes / 60;

            timeSlotMinute = durationMinutes - (60 * timeSlotHour);

        }else {

            timeSlotMinute = durationMinutes;

        }

    }

    public void putDate(Calendar date){

        put(AppointmentAttributes.date, date.getTime());

    }

    public int[] getDuration() {

        int[] duration = new int[2];

        int durationHours = 0;

        int durationMinutes = getInt(AppointmentAttributes.duration);

        if (durationMinutes >= 60) {

            durationHours = durationMinutes / 60;

            durationMinutes = durationMinutes - (60 * durationHours);

        }

        duration[0] = durationHours;

        duration[1] = durationMinutes;

        return duration;

    }

    public ParseRelation getServices(){

    return (ParseRelation) get(AppointmentAttributes.services);

    }

    public Date getDate() {

        return getDate(AppointmentAttributes.date);

    }

    public Venue getVenue() {

        return (Venue) get(AppointmentAttributes.venue);

    }

    public void setDate(Calendar date) {

        put(AppointmentAttributes.date, date.getTime());

    }

    public void setDuration(int duration) {

        put(AppointmentAttributes.duration, duration);

    }

    public void setVenue(Venue venue) {

        put(AppointmentAttributes.venue, venue);

    }

    public void setUser(User user) {

        put(AppointmentAttributes.user, user.getParseUser());

    }

    public User getUser(){

        User user = new User();

        user.setParseUser(getParseUser(AppointmentAttributes.user));

        return user;

    }

    public static ParseQuery<Appointment> getQuery() {

        return ParseQuery.getQuery(Appointment.class);

    }

}

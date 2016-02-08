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

    public static ParseQuery<Appointment> getQuery() {

        return ParseQuery.getQuery(Appointment.class);

    }

}

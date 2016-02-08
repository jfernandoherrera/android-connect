package com.example.techventures.tucitaconnect.model.context.appointment;

import com.example.techventures.tucitaconnect.model.domain.appointment.Appointment;
import com.example.techventures.tucitaconnect.model.domain.appointment.AppointmentAttributes;
import com.example.techventures.tucitaconnect.model.domain.user.User;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class AppointmentLocal {

    public Appointment getAppointment(String objectId){

        Appointment appointment = null;

        ParseQuery<Appointment> query = Appointment.getQuery();

        query.fromLocalDatastore();

        query.whereEqualTo(AppointmentAttributes.objectId, objectId);

        query.include(AppointmentAttributes.venue);

        try {

            appointment = query.getFirst();

        } catch (ParseException e) {

            e.printStackTrace();

        }

        return appointment;
    }

    public List loadUserAppointments(User user, int skip) {

        List venues = null;

        ParseQuery<Appointment> query = Appointment.getQuery();

        query.fromLocalDatastore();

        query.setSkip(skip);

        query.setLimit(4);

        query.whereEqualTo(AppointmentAttributes.user, user.getParseUser());

        query.orderByDescending(AppointmentAttributes.date);

        query.include(AppointmentAttributes.venue);

        try {

            venues = query.find();

        } catch (ParseException e) {

            e.printStackTrace();

        }

        return venues;

    }

}

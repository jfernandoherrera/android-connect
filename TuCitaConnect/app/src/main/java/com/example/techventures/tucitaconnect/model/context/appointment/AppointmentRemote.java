package com.example.techventures.tucitaconnect.model.context.appointment;


import com.example.techventures.tucitaconnect.model.domain.appointment.Appointment;
import com.example.techventures.tucitaconnect.model.domain.appointment.AppointmentAttributes;
import com.example.techventures.tucitaconnect.model.domain.user.User;
import com.example.techventures.tucitaconnect.model.domain.venue.Venue;
import com.example.techventures.tucitaconnect.model.domain.venue.VenueAttributes;
import com.example.techventures.tucitaconnect.model.error.AppError;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AppointmentRemote {

    private ParseQuery<Appointment> query;

    private void setQuery() {

        query = Appointment.getQuery();

    }

    public void cancelQuery() {

        if (query != null) {

            query.cancel();

        }

    }

    public void getAppointment(String objectId, final AppointmentCompletion.AppointmentErrorCompletion completion){

        setQuery();

        query.whereEqualTo(AppointmentAttributes.objectId, objectId);

        query.include(AppointmentAttributes.venue);

        query.getFirstInBackground(new GetCallback<Appointment>() {

            @Override
            public void done(Appointment object, ParseException e) {

                AppError appError = e != null ? new AppError(Appointment.class.toString(), 0, null) : null;

                completion.completion(object, appError);
            }
        });
    }

    public void loadAppointmentsDateVenue(Venue venue, Calendar date, final AppointmentCompletion.AppointmentErrorCompletion completion) {

        setQuery();

        date.set(Calendar.HOUR_OF_DAY, 0);

        date.set(Calendar.MINUTE, 0);

        date.set(Calendar.SECOND, 0);

        query.whereGreaterThan(AppointmentAttributes.date, date.getTime());

        date.add(Calendar.DATE, 1);

        Date oneMoreDay = date.getTime();

        query.whereLessThan(AppointmentAttributes.date, oneMoreDay);

        query.whereEqualTo(AppointmentAttributes.venue, venue);

        query.include(AppointmentAttributes.user);

        query.findInBackground(new FindCallback<Appointment>() {

            @Override
            public void done(List<Appointment> objects, ParseException e) {

                AppError appError = e != null ? new AppError(Appointment.class.toString(), 0, null) : null;

                completion.completion(objects, appError);

            }

        });

        date.add(Calendar.DATE, -1);

    }

    public void placeOrder(Appointment appointment, final AppointmentCompletion.AppointmentErrorCompletion completion) {

        appointment.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e) {

                if(e != null){

                    e.printStackTrace();

                }

                AppError appError = e != null ? new AppError(Appointment.class.toString(), 0, null) : null;

                completion.completion(new Appointment(), appError);

            }

        });

    }

    public void loadUserAppointments(User user, final AppointmentCompletion.AppointmentErrorCompletion completion, int skip) {

        ParseQuery<Appointment> query = Appointment.getQuery();

        query.whereEqualTo(AppointmentAttributes.user, user.getParseUser());

        query.orderByDescending(AppointmentAttributes.date);

        query.setSkip(skip);

        query.setLimit(4);

        query.include(AppointmentAttributes.venue);

        query.include(AppointmentAttributes.venue + "." + VenueAttributes.city);

        query.findInBackground(new FindCallback<Appointment>() {

            @Override
            public void done(List<Appointment> objects, ParseException e) {

                if (objects != null) {

                    try {

                        ParseObject.pinAll(objects);

                    } catch (ParseException e1) {

                        e1.printStackTrace();

                    }

                }

                AppError appError = e != null ? new AppError(Appointment.class.toString(), 0, null) : null;

                completion.completion(objects, appError);

            }

        });

    }

}

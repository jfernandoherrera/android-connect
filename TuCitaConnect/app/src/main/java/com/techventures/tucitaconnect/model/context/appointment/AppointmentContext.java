package com.techventures.tucitaconnect.model.context.appointment;



import com.techventures.tucitaconnect.model.domain.appointment.Appointment;
import com.techventures.tucitaconnect.model.domain.user.User;
import com.techventures.tucitaconnect.model.domain.venue.Venue;

import java.util.Calendar;
import java.util.List;

public class AppointmentContext {

    private AppointmentRemote appointmentRemote;
    private AppointmentLocal appointmentLocal;

    public static AppointmentContext context(AppointmentContext appointmentContext) {

        if (appointmentContext == null) {

            appointmentContext = new AppointmentContext();

        }

        return appointmentContext;

    }

    public AppointmentContext() {

        appointmentLocal = new AppointmentLocal();

        appointmentRemote = new AppointmentRemote();

    }

    public void loadAppointmentsDateVenue(Venue venue, Calendar date, AppointmentCompletion.AppointmentErrorCompletion completion) {

        appointmentRemote.loadAppointmentsDateVenue(venue, date, completion);

    }

    public void placeOrder(Appointment appointment, AppointmentCompletion.AppointmentErrorCompletion completion) {

        appointmentRemote.placeOrder(appointment, completion);

    }

    public List loadUserAppointments(User user, AppointmentCompletion.AppointmentErrorCompletion completion, int skip) {

        List appointments = appointmentLocal.loadUserAppointments(user, skip);

        appointmentRemote.loadUserAppointments(user, completion, skip);

        return appointments;

    }

    public Appointment getAppointment(String objectId, AppointmentCompletion.AppointmentErrorCompletion completion) {

        Appointment appointments = appointmentLocal.getAppointment(objectId);

        appointmentRemote.getAppointment(objectId, completion);

        return appointments;

    }

}

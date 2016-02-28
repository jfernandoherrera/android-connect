package com.techventures.tucitaconnect.model.context.appointment;


import com.techventures.tucitaconnect.model.domain.appointment.Appointment;
import com.techventures.tucitaconnect.model.error.AppError;

import java.util.List;

public class AppointmentCompletion {

    public interface AppointmentErrorCompletion {

        void completion(List<Appointment> appointmentList, AppError error);

        void completion(Appointment appointment, AppError error);

    }

}

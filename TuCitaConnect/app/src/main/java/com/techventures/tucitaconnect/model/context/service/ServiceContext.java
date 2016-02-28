package com.techventures.tucitaconnect.model.context.service;


import com.techventures.tucitaconnect.model.domain.appointment.Appointment;
import com.techventures.tucitaconnect.model.domain.service.Service;
import com.techventures.tucitaconnect.model.domain.venue.Venue;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.util.List;

public class ServiceContext {

    private ServiceLocal serviceLocal;
    private ServiceRemote serviceRemote;

    public void cancelQuery() {

        serviceRemote.cancelQuery();

    }

    public static ServiceContext context(ServiceContext serviceContext) {

        if (serviceContext == null) {

            serviceContext = new ServiceContext();

        }

        return serviceContext;

    }

    private ServiceContext() {

        serviceLocal = new ServiceLocal();

        serviceRemote = new ServiceRemote();

    }

    public List<Service> loadServices(Venue venue, ServiceCompletion.ErrorCompletion completion) {

        List services = null;

       /* ParseRelation object = venue.getServices();

        ParseQuery<Service> queryLocal = object.getQuery();

        services = serviceLocal.loadServices(queryLocal);

        ParseQuery<Service> queryRemote = object.getQuery();

        serviceRemote.loadServices(queryRemote, completion);
*/
        return services;

    }

    public void loadAppointmentServices(Appointment appointment, ServiceCompletion.ErrorCompletion completion){


        ParseRelation object = appointment.getServices();

        ParseQuery<Service> queryRemote = object.getQuery();

        serviceRemote.loadAppointmentServices(queryRemote, completion);

    }

}

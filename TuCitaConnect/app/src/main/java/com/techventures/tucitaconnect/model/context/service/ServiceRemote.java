package com.techventures.tucitaconnect.model.context.service;

import com.techventures.tucitaconnect.model.domain.service.Service;
import com.techventures.tucitaconnect.model.domain.service.ServiceAttributes;
import com.techventures.tucitaconnect.model.error.AppError;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class ServiceRemote {

    private ParseQuery<Service> query;

    private void setQuery() {

        query = Service.getQuery();

    }

    public void cancelQuery() {

        if (query != null) {

            query.cancel();

        }

    }


    public void loadServices(ParseQuery<Service> servicesRemoteQuery, final ServiceCompletion.ErrorCompletion completion) {

        query = servicesRemoteQuery;

        query.include(ServiceAttributes.subCategory);

        query.orderByAscending(ServiceAttributes.name);

        query.findInBackground(new FindCallback<Service>() {

            @Override
            public void done(List<Service> objects, ParseException e) {

                if (objects != null) {

                    try {

                        ParseObject.pinAll(objects);

                    } catch (ParseException pe) {
                    }

                }

                AppError appError = e != null ? new AppError(Service.class.toString(), 0, null) : null;

                completion.completion(objects, appError);

            }

        });

    }

    public void loadAppointmentServices(ParseQuery<Service> appointment, final ServiceCompletion.ErrorCompletion completion) {

        query = appointment;

        query.findInBackground(new FindCallback<Service>() {

            @Override
            public void done(List<Service> objects, ParseException e) {

                if(e!=null){

                  e.printStackTrace();

                }

                AppError appError = e != null ? new AppError(Service.class.toString(), 0, null) : null;

                completion.completion(objects, appError);

            }

        });

    }

}

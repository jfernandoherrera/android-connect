package com.example.techventures.tucitaconnect.model.context.service;


import com.example.techventures.tucitaconnect.model.domain.service.Service;
import com.example.techventures.tucitaconnect.model.domain.service.ServiceAttributes;
import com.parse.ParseQuery;

import java.util.List;

public class ServiceLocal {

    public List<Service> loadServices(ParseQuery<Service> servicesLocalQuery) {

        servicesLocalQuery.include(ServiceAttributes.subCategory);

        servicesLocalQuery.fromLocalDatastore();

        servicesLocalQuery.orderByAscending(ServiceAttributes.name);

        List serviceList = null;

        try {

            List services = servicesLocalQuery.find();

            if (services != null) {

                serviceList = services;

            }

        } catch (com.parse.ParseException e) {

            e.printStackTrace();

        }

        return serviceList;

    }

}

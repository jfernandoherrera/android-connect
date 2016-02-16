package com.example.techventures.tucitaconnect.model.context.service;


import com.example.techventures.tucitaconnect.model.domain.service.Service;
import com.example.techventures.tucitaconnect.model.error.AppError;

import java.util.List;

public class ServiceCompletion {

    public interface ErrorCompletion {

        void completion(List<Service> servicesList, AppError error);

    }

}



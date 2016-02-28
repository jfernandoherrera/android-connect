package com.techventures.tucitaconnect.model.context.service;


import com.techventures.tucitaconnect.model.domain.service.Service;
import com.techventures.tucitaconnect.model.error.AppError;

import java.util.List;

public class ServiceCompletion {

    public interface ErrorCompletion {

        void completion(List<Service> servicesList, AppError error);

    }

}



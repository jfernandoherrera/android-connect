package com.example.techventures.tucitaconnect.model.context.openingHour;


import com.example.techventures.tucitaconnect.model.domain.openingHour.OpeningHour;
import com.example.techventures.tucitaconnect.model.error.AppError;

import java.util.List;

public class OpeningHourCompletion {

    public interface OpeningHourErrorCompletion {

        void completion(List<OpeningHour> openingHourList, AppError error);

    }

}

package com.techventures.tucitaconnect.model.context.editedOpeningHour;

import com.techventures.tucitaconnect.model.domain.editedOpeningHour.EditedOpeningHour;
import com.techventures.tucitaconnect.model.domain.openingHour.OpeningHour;
import com.techventures.tucitaconnect.model.error.AppError;

import java.util.List;

public class EditedOpeningHourCompletion {

    public interface EditedOpeningHourErrorCompletion {

        void completion(List<EditedOpeningHour> editedOpeningHours, AppError error);

    }

    
}

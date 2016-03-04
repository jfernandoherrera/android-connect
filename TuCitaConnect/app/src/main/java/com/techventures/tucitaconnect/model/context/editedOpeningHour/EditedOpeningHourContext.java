package com.techventures.tucitaconnect.model.context.editedOpeningHour;

import com.techventures.tucitaconnect.model.context.service.ServiceLocal;
import com.techventures.tucitaconnect.model.context.service.ServiceRemote;
import com.techventures.tucitaconnect.model.domain.editedOpeningHour.EditedOpeningHour;
import com.techventures.tucitaconnect.model.domain.venue.Venue;

public class EditedOpeningHourContext {


    private EditedOpeningHourRemote editedOpeningHourRemote;



    public static EditedOpeningHourContext context(EditedOpeningHourContext editedOpeningHourContext) {

        if (editedOpeningHourContext == null) {

            editedOpeningHourContext = new EditedOpeningHourContext();

        }

        return editedOpeningHourContext;

    }

    private EditedOpeningHourContext() {

        editedOpeningHourRemote = new EditedOpeningHourRemote();

    }

    public void saveEditedOpeningHour(EditedOpeningHour editedOpeningHour, EditedOpeningHourCompletion.EditedOpeningHourErrorCompletion completion) {

        editedOpeningHourRemote.save(editedOpeningHour, completion);

    }

    public void getEditedOpeningHour(Venue venue, EditedOpeningHourCompletion.EditedOpeningHourErrorCompletion completion) {

        editedOpeningHourRemote.getEditedOpeningHours(venue, completion);

    }


}

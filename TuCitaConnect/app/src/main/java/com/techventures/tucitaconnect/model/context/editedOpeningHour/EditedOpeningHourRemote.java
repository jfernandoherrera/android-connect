package com.techventures.tucitaconnect.model.context.editedOpeningHour;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.techventures.tucitaconnect.model.domain.editedOpeningHour.EditedOpeningHour;
import com.techventures.tucitaconnect.model.domain.editedOpeningHour.EditedOpeningHourAttributes;
import com.techventures.tucitaconnect.model.domain.openingHour.OpeningHour;
import com.techventures.tucitaconnect.model.domain.service.Service;
import com.techventures.tucitaconnect.model.domain.venue.Venue;
import com.techventures.tucitaconnect.model.error.AppError;

import java.util.List;

public class EditedOpeningHourRemote {

    private ParseQuery<EditedOpeningHour> query;

    private void setQuery() {

        query = EditedOpeningHour.getQuery();

    }

    public void cancelQuery() {

        if (query != null) {

            query.cancel();

        }

    }

    public void save(EditedOpeningHour editedOpeningHour, final EditedOpeningHourCompletion.EditedOpeningHourErrorCompletion completion) {

        editedOpeningHour.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e) {

                AppError appError = e != null ? new AppError(EditedOpeningHour.class.toString(), 0, null) : null;

                completion.completion(null, appError);

            }
        });

    }



    public void getEditedOpeningHours(Venue venue, final EditedOpeningHourCompletion.EditedOpeningHourErrorCompletion completion) {

        setQuery();

        query.whereEqualTo(EditedOpeningHourAttributes.venue, venue);

        query.findInBackground(new FindCallback<EditedOpeningHour>() {

            @Override
            public void done(List<EditedOpeningHour> objects, ParseException e) {

                AppError appError = e != null ? new AppError(EditedOpeningHour.class.toString(), 0, null) : null;

                completion.completion(objects, appError);


            }
        });


    }
}

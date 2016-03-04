package com.techventures.tucitaconnect.model.domain.editedOpeningHour;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.techventures.tucitaconnect.model.domain.venue.Venue;

@ParseClassName("EditedOpeningHours")
public class EditedOpeningHour extends ParseObject{



    public void putVenue(Venue venue) {

        put(EditedOpeningHourAttributes.venue, venue);

    }

    public void putDurationMinutes(int durationMinutes) {

        put(EditedOpeningHourAttributes.durationMinutes, durationMinutes);

    }

    public static ParseQuery<EditedOpeningHour> getQuery() {

    return ParseQuery.getQuery(EditedOpeningHour.class);

    }
}

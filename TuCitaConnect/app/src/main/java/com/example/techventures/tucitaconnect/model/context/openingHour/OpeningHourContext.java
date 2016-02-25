package com.example.techventures.tucitaconnect.model.context.openingHour;


import com.example.techventures.tucitaconnect.model.domain.openingHour.OpeningHour;
import com.example.techventures.tucitaconnect.model.domain.venue.Venue;
import com.example.techventures.tucitaconnect.model.domain.venue.VenueAttributes;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.util.List;

public class OpeningHourContext {

    private OpeningHourLocal openingHourLocal;
    private OpeningHourRemote openingHourRemote;

    public OpeningHourContext() {

        openingHourLocal = new OpeningHourLocal();

        openingHourRemote = new OpeningHourRemote();

    }

    public void cancelQuery() {

        openingHourRemote.cancelQuery();

    }

    public static OpeningHourContext context(OpeningHourContext openingHourContext) {

        if (openingHourContext == null) {

            openingHourContext = new OpeningHourContext();

        }

        return openingHourContext;

    }

    public void loadDayOpeningHours(Venue venue, int day, OpeningHourCompletion.OpeningHourErrorCompletion completion) {

        ParseRelation object = (ParseRelation) venue.get(VenueAttributes.openingHours);

        ParseQuery<OpeningHour> queryRemote = object.getQuery();

        openingHourRemote.loadDayOpeningHours(queryRemote, day, completion);

    }

    public List<OpeningHour> loadOpeningHours(Venue venue, OpeningHourCompletion.OpeningHourErrorCompletion completion) {

        List openingHours;

        ParseRelation object = (ParseRelation) venue.get(VenueAttributes.openingHours);

        ParseQuery<OpeningHour> queryLocal = object.getQuery();

        openingHours = openingHourLocal.loadOpeningHours(queryLocal);

        ParseQuery<OpeningHour> queryRemote = object.getQuery();

        openingHourRemote.loadOpeningHours(queryRemote, completion);

        return openingHours;

    }

}

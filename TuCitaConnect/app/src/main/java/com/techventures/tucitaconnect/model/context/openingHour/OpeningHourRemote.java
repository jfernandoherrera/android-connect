package com.techventures.tucitaconnect.model.context.openingHour;

import android.util.Log;

import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseRelation;
import com.parse.SaveCallback;
import com.techventures.tucitaconnect.activities.venue.VenueActivity;
import com.techventures.tucitaconnect.model.domain.openingHour.OpeningHour;
import com.techventures.tucitaconnect.model.domain.openingHour.OpeningHourAttributes;
import com.techventures.tucitaconnect.model.domain.venue.Venue;
import com.techventures.tucitaconnect.model.domain.venue.VenueAttributes;
import com.techventures.tucitaconnect.model.error.AppError;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class OpeningHourRemote {

    private ParseQuery<OpeningHour> query;

    public void cancelQuery() {

        if (query != null) {

            query.cancel();

        }

    }

    public void deleteOpeningHouir(OpeningHour openingHour, final OpeningHourCompletion.OpeningHourErrorCompletion completion) {

        openingHour.deleteInBackground(new DeleteCallback() {

            @Override
            public void done(ParseException e) {

                AppError appError = e != null ? new AppError(OpeningHour.class.toString(), 0, null) : null;

                completion.completion(null, appError);

            }
        });

    }

    public void saveOpeningHours(final List<OpeningHour> openingHours, final Venue venue, final OpeningHourCompletion.OpeningHourErrorCompletion completion) {


        ParseRelation openingHourParseRelation = (ParseRelation) venue.get(VenueAttributes.openingHours);

                    try {

                        ParseObject.saveAll(openingHours);

                    for (OpeningHour openingHour : openingHours) {

                        openingHourParseRelation.add(openingHour);

                    }

                    venue.saveInBackground(new SaveCallback() {

                        @Override
                        public void done(ParseException e) {

                            AppError appError = e != null ? new AppError(OpeningHour.class.toString(), 0, null) : null;

                            completion.completion(null, appError);

                        }

                    });
                    } catch (ParseException e1) {

                        e1.printStackTrace();

                    }
                }


    public void loadOpeningHours(ParseQuery<OpeningHour> openingHourRemoteQuery, final OpeningHourCompletion.OpeningHourErrorCompletion completion) {

        query = openingHourRemoteQuery;

        query.findInBackground(new FindCallback<OpeningHour>() {

            @Override
            public void done(List<OpeningHour> objects, ParseException e) {

                if (objects != null) {

                 /*   try {

                        ParseObject.pinAll(objects);

                    } catch (ParseException pe) {}
*/
                }

                AppError appError = e != null ? new AppError(OpeningHour.class.toString(), 0, null) : null;

                completion.completion(objects, appError);

            }

        });

    }

    public void loadDayOpeningHours(ParseQuery<OpeningHour> openingHourRemoteQuery, int day, final OpeningHourCompletion.OpeningHourErrorCompletion completion) {

        query = openingHourRemoteQuery;

        query.whereEqualTo(OpeningHourAttributes.day, day);

        query.findInBackground(new FindCallback<OpeningHour>() {

            @Override
            public void done(List<OpeningHour> objects, ParseException e) {

                if (objects != null) {

                    try {

                        ParseObject.pinAll(objects);

                    } catch (ParseException pe) {}

                }

                AppError appError = e != null ? new AppError(OpeningHour.class.toString(), 0, null) : null;

                completion.completion(objects, appError);

            }

        });

    }

}

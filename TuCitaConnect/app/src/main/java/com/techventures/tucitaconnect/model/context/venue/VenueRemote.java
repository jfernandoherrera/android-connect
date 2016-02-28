package com.techventures.tucitaconnect.model.context.venue;

import android.location.Location;

import com.techventures.tucitaconnect.model.domain.city.City;
import com.techventures.tucitaconnect.model.domain.city.CityAttributes;
import com.techventures.tucitaconnect.model.domain.venue.Venue;
import com.techventures.tucitaconnect.model.domain.venue.VenueAttributes;
import com.techventures.tucitaconnect.model.error.AppError;
import com.techventures.tucitaconnect.utils.common.attributes.CommonAttributes;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class VenueRemote {

    private ParseQuery<Venue> query;

    private void setQuery() {

        query = Venue.getQuery();

    }

    public void cancelQuery() {

        if (query != null) {

            query.cancel();

        }

    }

    public void loadVenues(ParseQuery<Venue> parseQuery, final VenueCompletion.ListErrorCompletion completion){

        query = parseQuery;

        query.include(VenueAttributes.city);

        query.orderByAscending(VenueAttributes.name);

        query.findInBackground(new FindCallback<Venue>() {

            @Override
            public void done(List<Venue> objects, com.parse.ParseException e) {

                if(e != null) {

                    e.printStackTrace();

                }

                AppError appError = e != null ? new AppError(VenueContext.class.toString(), 0, null) : null;

                completion.completion(objects, appError);

            }

        });

    }

    public void getVenue(String objectId, final VenueCompletion.ErrorCompletion completion) {

        setQuery();

        query.whereEqualTo(CommonAttributes.objectId, objectId);

        query.getFirstInBackground(new GetCallback<Venue>() {
            @Override
            public void done(Venue object, ParseException e) {

                AppError appError = e != null ? new AppError(VenueContext.class.toString(), 0, null) : null;

                completion.completion(object, appError);
            }
        });

    }


        public void findVenue(String lookThat, String address, final VenueCompletion.ListErrorCompletion completion) {

        ParseQuery queryName = Venue.getQuery();

        queryName.whereEqualTo(VenueAttributes.name, lookThat);

        ParseQuery queryAddress = Venue.getQuery();

        queryAddress.whereEqualTo(VenueAttributes.address, address);

        List<ParseQuery<ParseObject>> queries = new ArrayList<>();

        queries.add(queryAddress);

        queries.add(queryName);

        ParseQuery queryTemp = ParseQuery.or(queries);

        query = queryTemp;

        query.include(VenueAttributes.city);

        query.findInBackground(new FindCallback<Venue>() {

            @Override
            public void done(List<Venue> objects, ParseException e) {

                if (e != null) {

                    e.printStackTrace();

                }

                if (objects != null) {

                    try {

                        ParseObject.pinAll(objects);

                    } catch (ParseException pe) {

                        pe.printStackTrace();

                    }

                }

                AppError appError = e != null ? new AppError(Venue.class.toString(), 0, null) : null;

                completion.completion(objects, appError);

            }

        });

    }


}

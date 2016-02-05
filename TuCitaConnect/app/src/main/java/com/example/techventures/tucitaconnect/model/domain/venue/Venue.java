package com.example.techventures.tucitaconnect.model.domain.venue;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.techventures.tucitaconnect.model.domain.city.City;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Venue")

public class Venue extends ParseObject {


    public void setName(String name) {

        put(VenueAttributes.name, name);

    }

    public double getLatitude() {

        return getLocation().getLatitude();

    }

    public double getLongitude() {

        return getLocation().getLongitude();

    }

    public ParseGeoPoint getLocation() {

        return getParseGeoPoint(VenueAttributes.location);

    }

    public String getName() {

        return getString(VenueAttributes.name);

    }

    public String getDescription() {

        return getString(VenueAttributes.description);

    }

    public int getReviews(){

        return getInt(VenueAttributes.reviewsCount);

    }

    public double getRating() {

        return getDouble(VenueAttributes.rating);

    }

    public Bitmap getPicture() {

        ParseFile picture = getParseFile(VenueAttributes.picture);

        Bitmap bm = null;

        if (picture != null) {

            try {

                bm = BitmapFactory.decodeByteArray(picture.getData(), 0, picture.getData().length);

            } catch (ParseException e) {

                e.printStackTrace();

            }

        }

        return bm;

    }

    public String getAddress() {

        return getString(VenueAttributes.address);

    }

    public City getCity() {

        return (City) get(VenueAttributes.city);

    }

    public static ParseQuery<Venue> getQuery() {

        return ParseQuery.getQuery(Venue.class);

    }

}

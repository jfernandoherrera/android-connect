package com.techventures.tucitaconnect.model.context.venue;

import com.techventures.tucitaconnect.model.domain.city.City;
import com.techventures.tucitaconnect.model.domain.city.CityAttributes;
import com.techventures.tucitaconnect.model.domain.venue.Venue;
import com.techventures.tucitaconnect.model.domain.venue.VenueAttributes;
import com.techventures.tucitaconnect.utils.common.attributes.CommonAttributes;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.List;

public class VenueLocal {

    public Venue findVenue(String lookThat, String address) {

        ParseQuery queryName = Venue.getQuery();

        queryName.whereEqualTo(VenueAttributes.name, lookThat);

        ParseQuery queryAddress = Venue.getQuery();

        queryAddress.whereEqualTo(VenueAttributes.address, address);

        List<ParseQuery<ParseObject>> queries = new ArrayList<>();

        queries.add(queryAddress);

        queries.add(queryName);

        ParseQuery query = ParseQuery.or(queries);

        query.include(VenueAttributes.city);

        query.fromLocalDatastore();

        Venue venue = null;

        try {

            venue = (Venue) query.getFirst();

        } catch (ParseException e) {

            e.printStackTrace();

        }

        return venue;

    }

    public List<Venue> loadRecentVenues() {

        ParseQuery<Venue> queryName = Venue.getQuery();

        queryName.fromLocalDatastore();

        queryName.orderByAscending(VenueAttributes.updatedAt);

        queryName.setLimit(10);

        queryName.include(VenueAttributes.city);

        List venuesList = new ArrayList();

        try {

            List venues = queryName.find();

            if (venues != null) {

                venuesList = venues;

            }

        } catch (ParseException e) {

            e.printStackTrace();

        }

        return venuesList;

    }

    public Venue getVenue(String objectId){

        ParseQuery query = Venue.getQuery();

        query.fromLocalDatastore();

        query.whereEqualTo(CommonAttributes.objectId, objectId);

        Venue venue = null;

        try {

            venue = (Venue) query.getFirst();

        } catch (ParseException e) {

            e.printStackTrace();

        }

        return venue;

    }

    public List<Venue> loadLikeVenues(String likeWord) {

        ParseQuery queryName = Venue.getQuery();

        queryName.whereContains(VenueAttributes.nameToSearch, likeWord);

        ParseQuery queryAddress = Venue.getQuery();

        queryAddress.whereContains(VenueAttributes.addressToSearch, likeWord);

        ParseQuery queryCityName = City.getQuery();

        queryCityName.whereContains(CityAttributes.nameToSearch, likeWord);

        ParseQuery queryVenueCity = Venue.getQuery();

        queryVenueCity.whereMatchesQuery(VenueAttributes.city, queryCityName);

        ParseQuery queryCityDepartment = City.getQuery();

        queryCityDepartment.whereContains(CityAttributes.departmentToSearch, likeWord);

        ParseQuery queryVenueDepartment = Venue.getQuery();

        queryVenueDepartment.whereMatchesQuery(VenueAttributes.city, queryCityDepartment);

        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();

        queries.add(queryAddress);

        queries.add(queryName);

        queries.add(queryVenueCity);

        queries.add(queryVenueDepartment);

        ParseQuery query = ParseQuery.or(queries);

        query.include(VenueAttributes.city);

        query.fromLocalDatastore();

        query.orderByAscending(VenueAttributes.name);

        List<Venue> venueList = new ArrayList<>();

        try {

            venueList = query.find();

        } catch (ParseException e) {

            e.printStackTrace();

        }

        return venueList;

    }

}

package com.techventures.tucitaconnect.model.context.venue;


import com.techventures.tucitaconnect.model.domain.user.User;
import com.techventures.tucitaconnect.model.domain.venue.Venue;
import com.parse.ParseQuery;
import com.parse.ParseRelation;


public class VenueContext {

    private VenueRemote venueRemote;
    private VenueLocal venueLocal;

    public static VenueContext context(VenueContext venueContext) {

        if (venueContext == null) {

            venueContext = new VenueContext();

        }

        return venueContext;

    }

    VenueContext() {

        venueLocal = new VenueLocal();

        venueRemote = new VenueRemote();

    }

    public Venue findVenue(String lookThat, String address, VenueCompletion.ListErrorCompletion completion) {

        Venue venue =null;
        //= venueLocal.findVenue(lookThat, address);

        venueRemote.findVenue(lookThat, address, completion);

        return venue;

    }

    public Venue getVenue(String objectId, VenueCompletion.ErrorCompletion completion){

        Venue venue=null;
        //= venueLocal.getVenue(objectId);

        venueRemote.getVenue(objectId, completion);

       return venue;

    }

    public void cancelQuery() {

        venueRemote.cancelQuery();

    }

    public void loadVenues(User user, final VenueCompletion.ListErrorCompletion completion){

        ParseRelation<Venue> parseRelation = user.getVenues();

        ParseQuery<Venue> parseQuery = parseRelation.getQuery();

        venueRemote.loadVenues(parseQuery, completion);

    }

}
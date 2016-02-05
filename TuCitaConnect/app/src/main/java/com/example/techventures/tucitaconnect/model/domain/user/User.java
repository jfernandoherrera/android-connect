package com.example.techventures.tucitaconnect.model.domain.user;


import com.example.techventures.tucitaconnect.model.domain.venue.Venue;
import com.example.techventures.tucitaconnect.model.domain.venue.VenueAttributes;
import com.parse.ParseRelation;
import com.parse.ParseUser;

public class User {

    private ParseUser parseUser;

    public void setParseUser(ParseUser user) {

        parseUser = user;

    }

    public ParseUser getParseUser() {

        return parseUser;

    }

    public String getObjectId(){

        return parseUser.getObjectId();
    }

    public void setUserName(String name) {

        parseUser.put(UserAttributes.username, name);

    }

    public ParseRelation getVenues(){

        return (ParseRelation) parseUser.get(UserAttributes.venues);

    }

    public String getUserName() {

        return parseUser.getString(UserAttributes.username);

    }

    public void setEmail(String email) {

        parseUser.put(UserAttributes.email, email);

    }

    public void setName(String name) {

        parseUser.put(UserAttributes.name, name);

    }

    public String getName() {

        return parseUser.getString(UserAttributes.name);

    }

    public String getEmail() {

        return parseUser.getString(UserAttributes.email);

    }

}

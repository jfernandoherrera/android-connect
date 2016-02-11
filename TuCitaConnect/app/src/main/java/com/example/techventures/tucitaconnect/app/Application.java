package com.example.techventures.tucitaconnect.app;

import com.example.techventures.tucitaconnect.model.domain.appointment.Appointment;
import com.example.techventures.tucitaconnect.model.domain.city.City;
import com.example.techventures.tucitaconnect.model.domain.slot.Slot;
import com.example.techventures.tucitaconnect.model.domain.venue.Venue;
import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;

public class Application extends android.app.Application {

    public void onCreate() {

        super.onCreate();

       // Parse.enableLocalDatastore(this);

        FacebookSdk.sdkInitialize(this);

        ParseObject.registerSubclass(Venue.class);

        ParseObject.registerSubclass(Slot.class);

        ParseObject.registerSubclass(Appointment.class);

        ParseObject.registerSubclass(City.class);

        Parse.initialize(this);

        ParseFacebookUtils.initialize(this);

    }

}

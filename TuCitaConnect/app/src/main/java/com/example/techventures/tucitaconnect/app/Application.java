package com.example.techventures.tucitaconnect.app;

import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;

public class Application extends android.app.Application {

    public void onCreate() {

        super.onCreate();

        Parse.enableLocalDatastore(this);

        FacebookSdk.sdkInitialize(this);

        Parse.initialize(this);

        ParseFacebookUtils.initialize(this);

    }

}

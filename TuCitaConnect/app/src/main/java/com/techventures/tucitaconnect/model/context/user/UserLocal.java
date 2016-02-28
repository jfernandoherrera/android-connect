package com.techventures.tucitaconnect.model.context.user;


import com.techventures.tucitaconnect.model.domain.user.User;
import com.parse.ParseUser;

public class UserLocal {

    public User currentUser() {

        User user = null;

        ParseUser parseUser = ParseUser.getCurrentUser();

        if (parseUser != null) {

            user = new User();

            user.setParseUser(parseUser);

        }

        return user;

    }

    public void logout() {

        ParseUser.logOut();

    }

}

package com.techventures.tucitaconnect.activities.venue.fragments;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.techventures.tucitaconnect.R;
import com.techventures.tucitaconnect.model.context.user.UserCompletion;
import com.techventures.tucitaconnect.model.context.user.UserContext;
import com.techventures.tucitaconnect.model.domain.user.User;
import com.techventures.tucitaconnect.model.domain.venue.Venue;
import com.techventures.tucitaconnect.model.error.AppError;

public class AddUserFragment extends DialogFragment{

    private UserContext userContext;
    private Venue venue;

    @Override
    public void onAttach(Context context) {

        setStyle(DialogFragment.STYLE_NO_TITLE, 0);

        super.onAttach(context);

    }

    public void setVenue(Venue venue) {

        this.venue = venue;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_user, container, false);

        userContext = UserContext.context(userContext);

        final TextView textView = (TextView) rootView.findViewById(R.id.userId);

        Button button = (Button) rootView.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                userContext.addVenueToUser(venue, String.valueOf(textView.getText()), new UserCompletion.UserErrorCompletion() {

                    @Override
                    public void completion(User user, AppError error) {


                    }
                });

            }
        });

    return rootView;

    }

}

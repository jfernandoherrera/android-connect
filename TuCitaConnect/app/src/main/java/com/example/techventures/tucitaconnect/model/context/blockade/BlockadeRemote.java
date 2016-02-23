package com.example.techventures.tucitaconnect.model.context.blockade;


import android.util.Log;

import com.example.techventures.tucitaconnect.model.domain.blockade.Blockade;
import com.example.techventures.tucitaconnect.model.domain.service.Service;
import com.example.techventures.tucitaconnect.model.domain.venue.Venue;
import com.example.techventures.tucitaconnect.model.domain.venue.VenueAttributes;
import com.example.techventures.tucitaconnect.model.error.AppError;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.SaveCallback;

import java.util.List;

public class BlockadeRemote {


    public void getBlockadeFromVenue(Venue venue, BlockadeCompletion.ErrorCompletion completion){



    }

    public void saveBlockade(Blockade blockade, Venue venue, final BlockadeCompletion.ErrorCompletion completion) {

        try {

           blockade.save();

            ParseRelation<Blockade> blockadeParseRelation = venue.getRelation(VenueAttributes.blockades);

            blockadeParseRelation.add(blockade);

            Log.i("" + venue.getObjectId(), blockadeParseRelation.toString() );

            venue.saveInBackground(new SaveCallback() {

                @Override
                public void done(ParseException e) {

                    AppError appError = e != null ? new AppError(Blockade.class.toString(), 0, null) : null;

                    if (e != null) {

                        e.printStackTrace();

                    }
                    completion.completion(null, appError);

                }
            });

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

}

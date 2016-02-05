package com.example.techventures.tucitaconnect.model.context.venue;


import com.example.techventures.tucitaconnect.model.domain.venue.Venue;
import com.example.techventures.tucitaconnect.model.error.AppError;

import java.util.List;

public class VenueCompletion {

    public interface ListErrorCompletion {

        void completion(List<Venue> venueList, AppError error);

    }
    public interface ErrorCompletion {

        void completion(Venue venue, AppError error);

    }


}

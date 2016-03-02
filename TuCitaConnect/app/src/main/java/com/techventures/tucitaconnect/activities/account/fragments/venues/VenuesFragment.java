package com.techventures.tucitaconnect.activities.account.fragments.venues;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.techventures.tucitaconnect.R;
import com.techventures.tucitaconnect.activities.account.adapters.VenuesAdapter;
import com.techventures.tucitaconnect.model.context.venue.VenueCompletion;
import com.techventures.tucitaconnect.model.context.venue.VenueContext;
import com.techventures.tucitaconnect.model.domain.user.User;
import com.techventures.tucitaconnect.model.domain.venue.Venue;
import com.techventures.tucitaconnect.model.error.AppError;
import com.techventures.tucitaconnect.utils.common.fragment.AppFragment;

import java.util.List;

public class VenuesFragment extends AppFragment {

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private VenueContext venueContext;
    private TextView noResults;
    private List<Venue> venues;
    private User user;
    private RelativeLayout relativeLayout;

    public void setUser(User user) {

        this.user = user;

    }

    private void setupVenues() {

        if(venueContext == null) {

            venueContext = VenueContext.context(null);

        }

            venueContext.loadVenues(user, new VenueCompletion.ListErrorCompletion() {

                @Override
                public void completion(List<Venue> venueList, AppError error) {

                    venues = venueList;

                    setupList();

                }
            });

    }


    public void setupList() {

        relativeLayout.setVisibility(View.GONE);

        if (venues != null && !venues.isEmpty()) {

            String reviewBy = getString(R.string.review_by);

            String users = getString(R.string.users);

            adapter = new VenuesAdapter(venues, reviewBy, users, typeface);

            recyclerView.setAdapter(adapter);

            noResults.setVisibility(View.GONE);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_venues, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        noResults = (TextView) rootView.findViewById(R.id.noResults);

        noResults.setTypeface(typeface);

        venueContext = VenueContext.context(venueContext);

        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.concealer);

        layoutManager = new GridLayoutManager(getContext(), 1);

        recyclerView.setLayoutManager(layoutManager);

        setupVenues();

        return rootView;

    }

}

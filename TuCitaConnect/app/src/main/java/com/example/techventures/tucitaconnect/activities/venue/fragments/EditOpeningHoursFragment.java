package com.example.techventures.tucitaconnect.activities.venue.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.techventures.tucitaconnect.R;
import com.example.techventures.tucitaconnect.model.context.openingHour.OpeningHourCompletion;
import com.example.techventures.tucitaconnect.model.context.openingHour.OpeningHourContext;
import com.example.techventures.tucitaconnect.model.domain.openingHour.OpeningHour;
import com.example.techventures.tucitaconnect.model.domain.venue.Venue;
import com.example.techventures.tucitaconnect.model.error.AppError;
import com.example.techventures.tucitaconnect.utils.common.views.DayOpeningHoursView;
import com.example.techventures.tucitaconnect.utils.common.views.StateButton;

import java.util.List;


public class EditOpeningHoursFragment extends DialogFragment {

    private OnTimeSelected listener;
    private OpeningHourContext openingHourContext;
    private List<OpeningHour> openingHours;
    private Venue venue;
    private Toolbar toolbar;

    public interface OnTimeSelected{

        void onTimeSelected(View view);

    }

    public void setVenue(Venue venue) {

        this.venue = venue;

    }

    private  DayOpeningHoursView getDayStateView(int day, View view) {

        DayOpeningHoursView stateButton = null;

        switch (day) {

            case 1:

                stateButton = ( DayOpeningHoursView) view.findViewById(R.id.mondayBusiness);

                break;

            case 2:

                stateButton = ( DayOpeningHoursView) view.findViewById(R.id.tuesdayBusiness);

                break;

            case 3:

                stateButton = ( DayOpeningHoursView) view.findViewById(R.id.wednesdayBusiness);

                break;

            case 4:

                stateButton = ( DayOpeningHoursView) view.findViewById(R.id.thursdayBusiness);

                break;

            case 5:

                stateButton = ( DayOpeningHoursView) view.findViewById(R.id.fridayBusiness);

                break;

            case 6:

                stateButton = ( DayOpeningHoursView) view.findViewById(R.id.saturdayBusiness);

                break;

            case 0:

                stateButton = ( DayOpeningHoursView) view.findViewById(R.id.sundayBusiness);

                break;

        }

        return stateButton;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_edit_opening_hours, container, false);

        openingHourContext = OpeningHourContext.context(openingHourContext);

        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        toolbar.inflateMenu(R.menu.menu_all_set);

        setup(rootView);

        return rootView;

    }

    private void setup(View view) {

    setupOpeningHours(view);

    }

    private void setupOpeningHours(final View view) {

            List<OpeningHour> openingHoursList = openingHourContext.loadOpeningHours(venue, new OpeningHourCompletion.OpeningHourErrorCompletion() {

                @Override
                public void completion(List<OpeningHour> openingHoursList, AppError error) {

                    if (openingHoursList != null && !openingHoursList.isEmpty()) {

                       openingHours = openingHoursList;

                    }

                    populateOpeningHours(view);

                }

            });

        openingHours = openingHoursList;

    }

    private void populateOpeningHours(View view) {

        for(int day = 0; day < 7; day ++) {

            final  DayOpeningHoursView stateButton = getDayStateView(day, view);

            stateButton.setDay(day);

            for(final OpeningHour openingHour : openingHours) {
                
                stateButton.setOnClickListener(new View.OnClickListener() {
                   
                    @Override
                    public void onClick(View v) {
                   
                        stateButton.clickState();

                    }

                });

                if(openingHour.getDay() == day) {
                    
                    stateButton.clickState();

                    stateButton.setOpeningHour(openingHour.getStartHour(), openingHour.getStartMinute(), openingHour.getEndHour(), openingHour.getEndMinute());

                }

            }

        }

    }

    @Override
    public void onAttach(Context context) {

        setStyle(DialogFragment.STYLE_NO_TITLE, 0);

        super.onAttach(context);

        listener = (OnTimeSelected) context;

    }

}

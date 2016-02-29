package com.techventures.tucitaconnect.activities.venue.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.techventures.tucitaconnect.R;
import com.techventures.tucitaconnect.activities.splash.SplashActivity;
import com.techventures.tucitaconnect.model.context.openingHour.OpeningHourCompletion;
import com.techventures.tucitaconnect.model.context.openingHour.OpeningHourContext;
import com.techventures.tucitaconnect.model.context.slot.SlotCompletion;
import com.techventures.tucitaconnect.model.context.slot.SlotContext;
import com.techventures.tucitaconnect.model.domain.openingHour.OpeningHour;
import com.techventures.tucitaconnect.model.domain.slot.Slot;
import com.techventures.tucitaconnect.model.domain.venue.Venue;
import com.techventures.tucitaconnect.model.error.AppError;
import com.techventures.tucitaconnect.utils.common.AlertDialogError;
import com.techventures.tucitaconnect.utils.common.views.DayOpeningHoursView;
import com.techventures.tucitaconnect.utils.common.views.StateButton;

import java.util.ArrayList;
import java.util.List;


public class EditOpeningHoursFragment extends DialogFragment {

    private OnTimeSelected listener;
    private OpeningHourContext openingHourContext;
    private List<OpeningHour> openingHours;
    private Venue venue;
    private Toolbar toolbar;
    private SlotContext slotContext;
    private EditText textDuration;

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

        slotContext = SlotContext.context(slotContext);

        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        textDuration = (EditText) rootView.findViewById(R.id.textDuration);

        toolbar.inflateMenu(R.menu.menu_all_set);

        MenuItem allSet = toolbar.getMenu().getItem(0);

        allSet.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                save();

                return false;
            }

        });

        setup(rootView);

        return rootView;

    }

    private void save() {

        final int weekDays = 7;

        openingHourContext.loadOpeningHours(venue, new OpeningHourCompletion.OpeningHourErrorCompletion() {

            @Override
            public void completion(List<OpeningHour> openingHourList, AppError error) {

                List<OpeningHour> toSave = new ArrayList<OpeningHour>();

                if(openingHourList != null) {

                    for(int count = 0; count < weekDays; count ++) {

                        OpeningHour sameDay = null;

                        for (OpeningHour openingHour : openingHourList) {

                            if (openingHour.getDay() == count) {

                                sameDay = openingHour;

                                break;

                            }

                        }

                        DayOpeningHoursView openingHoursView = getDayStateView(count, getView());

                        boolean isOpen = openingHoursView.getStateButton().getOpen();

                        if(sameDay != null) {

                            if(isOpen) {

                                sameDay.putStartHour(openingHoursView.getStartHour());

                                sameDay.putStartMinute(openingHoursView.getStartMinute());

                                sameDay.putEndHour(openingHoursView.getEndHour());

                                sameDay.putEndMinute(openingHoursView.getEndMinute());

                            } else {

                                openingHourContext.deleteOpeningHour(sameDay, new OpeningHourCompletion.OpeningHourErrorCompletion() {

                                    @Override
                                    public void completion(List<OpeningHour> openingHourList, AppError error) {

                                        if(error != null) {

                                            AlertDialogError alertDialogError = new AlertDialogError();

                                            alertDialogError.noInternetConnectionAlert(getContext());

                                        }

                                    }
                                });

                            }

                        } else {

                            if(isOpen) {

                                sameDay = new OpeningHour();

                                sameDay.putDay(count);

                                sameDay.putStartHour(openingHoursView.getStartHour());

                                sameDay.putStartMinute(openingHoursView.getStartMinute());

                                sameDay.putEndHour(openingHoursView.getEndHour());

                                sameDay.putEndMinute(openingHoursView.getEndMinute());

                            }

                        }

                        if(sameDay != null) {

                            toSave.add(sameDay);

                        }
                    }

                    openingHourContext.saveOpeningHours(toSave, venue, new OpeningHourCompletion.OpeningHourErrorCompletion() {

                        @Override
                        public void completion(List<OpeningHour> openingHourList, AppError error) {

                            if(error != null) {

                                AlertDialogError alertDialogError = new AlertDialogError();

                                alertDialogError.noInternetConnectionAlert(getContext());

                            } else {

                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(

                                        getContext());

                                String continueString = getString(R.string.continue_option);

                                String successful = getString(R.string.successful_transaction);

                                String message = getString(R.string.app_name);

                                alertDialogBuilder.setTitle(successful)

                                        .setPositiveButton(continueString, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int id) {

                                                SplashActivity.goToStart(getContext());

                                            }
                                        }).setCancelable(false)

                                        .setMessage(message).show();

                            }

                        }
                    });

                }

            }

        });

    }

    private void setup(View view) {

        setupDuration();

        setupOpeningHours(view);

    }

    private void setupDuration() {

        slotContext.getDuration(venue, new SlotCompletion.SlotErrorCompletion() {

            @Override
            public void completion(List<Slot> slotList, AppError error) {

            }

            @Override
            public void completion(int duration, AppError error) {

                if(error == null) {

                    textDuration.setText(String.valueOf(duration));

                }

            }

        });

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

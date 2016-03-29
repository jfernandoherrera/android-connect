package com.techventures.tucitaconnect.activities.venue.fragments.editopeninghour;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import com.techventures.tucitaconnect.R;
import com.techventures.tucitaconnect.activities.splash.SplashActivity;
import com.techventures.tucitaconnect.model.context.editedOpeningHour.EditedOpeningHourCompletion;
import com.techventures.tucitaconnect.model.context.editedOpeningHour.EditedOpeningHourContext;
import com.techventures.tucitaconnect.model.context.openingHour.OpeningHourCompletion;
import com.techventures.tucitaconnect.model.context.openingHour.OpeningHourContext;
import com.techventures.tucitaconnect.model.context.slot.SlotCompletion;
import com.techventures.tucitaconnect.model.context.slot.SlotContext;
import com.techventures.tucitaconnect.model.domain.editedOpeningHour.EditedOpeningHour;
import com.techventures.tucitaconnect.model.domain.openingHour.OpeningHour;
import com.techventures.tucitaconnect.model.domain.slot.Slot;
import com.techventures.tucitaconnect.model.domain.venue.Venue;
import com.techventures.tucitaconnect.model.error.AppError;
import com.techventures.tucitaconnect.utils.common.AlertDialogError;
import com.techventures.tucitaconnect.utils.common.views.DayOpeningHoursView;
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
    private OnEdited onEdited;
    private EditedOpeningHourContext editedOpeningHourContext;
    private RelativeLayout concealer;


    public interface OnTimeSelected {

        void onTimeSelected(Button view, int hour, int minute);

    }

    public interface OnEdited {

        void onEdited();

    }

    public void setVenue(Venue venue) {

        this.venue = venue;

    }

    private  DayOpeningHoursView getDayStateView(int day, View view) {

        DayOpeningHoursView stateButton = null;

        switch (day) {

            case 2:

                stateButton = ( DayOpeningHoursView) view.findViewById(R.id.mondayBusiness);

                break;

            case 3:

                stateButton = ( DayOpeningHoursView) view.findViewById(R.id.tuesdayBusiness);

                break;

            case 4:

                stateButton = ( DayOpeningHoursView) view.findViewById(R.id.wednesdayBusiness);

                break;

            case 5:

                stateButton = ( DayOpeningHoursView) view.findViewById(R.id.thursdayBusiness);

                break;

            case 6:

                stateButton = ( DayOpeningHoursView) view.findViewById(R.id.fridayBusiness);

                break;

            case 7:

                stateButton = ( DayOpeningHoursView) view.findViewById(R.id.saturdayBusiness);

                break;

            case 1:

                stateButton = ( DayOpeningHoursView) view.findViewById(R.id.sundayBusiness);

                break;

        }

        if(! stateButton.hasInitialDateListener()) {

            stateButton.setOnTimeSelected(listener);

        }

        return stateButton;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_edit_opening_hours, container, false);

        openingHourContext = OpeningHourContext.context(openingHourContext);

        editedOpeningHourContext = EditedOpeningHourContext.context(editedOpeningHourContext);

        slotContext = SlotContext.context(slotContext);

        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        textDuration = (EditText) rootView.findViewById(R.id.textDuration);

        toolbar.inflateMenu(R.menu.menu_all_set);

        concealer = (RelativeLayout) rootView.findViewById(R.id.concealer);

        concealer.setVisibility(View.VISIBLE);

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

        concealer.setVisibility(View.VISIBLE);

        final int duration;

        try {

            duration = Integer.parseInt(textDuration.getText().toString());

            final int weekDays = 7;

            openingHourContext.loadOpeningHours(venue, new OpeningHourCompletion.OpeningHourErrorCompletion() {

                @Override
                public void completion(List<OpeningHour> openingHourList, AppError error) {

                    List<OpeningHour> toSave = new ArrayList<OpeningHour>();

                    if (openingHourList != null) {

                        for (int count = 1; count <= weekDays; count++) {

                            OpeningHour sameDay = null;

                            for (OpeningHour openingHour : openingHourList) {

                                if (openingHour.getDay() == count) {

                                    sameDay = openingHour;

                                    break;

                                }

                            }

                            DayOpeningHoursView openingHoursView = getDayStateView(count, getView());

                            boolean isOpen = openingHoursView.getStateButton().getOpen();

                            if (sameDay != null) {

                                if (isOpen) {

                                    sameDay.putStartHour(openingHoursView.getStartHour());

                                    sameDay.putStartMinute(openingHoursView.getStartMinute());

                                    sameDay.putEndHour(openingHoursView.getEndHour());

                                    sameDay.putEndMinute(openingHoursView.getEndMinute());

                                } else {

                                    openingHourContext.deleteOpeningHour(sameDay, new OpeningHourCompletion.OpeningHourErrorCompletion() {

                                        @Override
                                        public void completion(List<OpeningHour> openingHourList, AppError error) {

                                            if (error != null) {

                                                AlertDialogError alertDialogError = new AlertDialogError();

                                                alertDialogError.noInternetConnectionAlert(getContext());

                                            }

                                        }
                                    });

                                }

                            } else {

                                if (isOpen) {

                                    sameDay = new OpeningHour();

                                    sameDay.putDay(count);

                                    sameDay.putStartHour(openingHoursView.getStartHour());

                                    sameDay.putStartMinute(openingHoursView.getStartMinute());

                                    sameDay.putEndHour(openingHoursView.getEndHour());

                                    sameDay.putEndMinute(openingHoursView.getEndMinute());

                                }

                            }

                            if (sameDay != null) {

                                toSave.add(sameDay);

                            }
                        }

                        openingHourContext.saveOpeningHours(toSave, venue, new OpeningHourCompletion.OpeningHourErrorCompletion() {

                            @Override
                            public void completion(List<OpeningHour> openingHourList, AppError error) {

                                if (error != null) {

                                    AlertDialogError alertDialogError = new AlertDialogError();

                                    alertDialogError.noInternetConnectionAlert(getContext());

                                } else {

                                    editedOpeningHourContext.getEditedOpeningHour(venue, new EditedOpeningHourCompletion.EditedOpeningHourErrorCompletion() {

                                        @Override
                                        public void completion(List<EditedOpeningHour> editedOpeningHours, AppError error) {

                                            final EditedOpeningHour editedOpeningHour;

                                            if (error == null && editedOpeningHours.size() > 0) {

                                                editedOpeningHour = editedOpeningHours.get(0);

                                            } else {

                                                editedOpeningHour = new EditedOpeningHour();

                                                editedOpeningHour.putVenue(venue);

                                            }

                                            editedOpeningHour.putDurationMinutes(duration);

                                            slotContext.destroyAllSlots(venue, new SlotCompletion.SlotErrorCompletion() {

                                                @Override
                                                public void completion(List<Slot> slotList, AppError error) {

                                                }

                                                @Override
                                                public void completion(int duration, AppError error) {

                                                    if(error != null) {

                                                        AlertDialogError alertDialogError = new AlertDialogError();

                                                        alertDialogError.noInternetConnectionAlert(getContext());

                                                    }else {

                                                        editedOpeningHourContext.saveEditedOpeningHour(editedOpeningHour, new EditedOpeningHourCompletion.EditedOpeningHourErrorCompletion() {

                                                            @Override
                                                            public void completion(List<EditedOpeningHour> editedOpeningHours, AppError error) {

                                                                concealer.setVisibility(View.GONE);

                                                                if (error == null) {

                                                                    onEdited.onEdited();

                                                                    dismiss();

                                                                } else {

                                                                    AlertDialogError alertDialogError = new AlertDialogError();

                                                                    alertDialogError.noInternetConnectionAlert(getContext());

                                                                }

                                                            }

                                                        });

                                                    }

                                                }

                                            });

                                        }

                                    });

                                }

                            }
                        });

                    }

                }

            });

        } catch (NumberFormatException exception) {

          AlertDialogError error = new AlertDialogError();

            error.mustBeANumber(getContext());

        }

    }

    private void showSuccessfulTransaction() {

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

    private void setup(View view) {

        setupDuration();

        setupOpeningHours(view);

    }



    private void setupDuration() {

        if(slotContext != null && venue != null) {

            slotContext.getDuration(venue, new SlotCompletion.SlotErrorCompletion() {

                @Override
                public void completion(List<Slot> slotList, AppError error) {

                }

                @Override
                public void completion(int duration, AppError error) {

                    if (error == null) {

                        textDuration.setText(String.valueOf(duration));

                    }

                }

            });

        }else {

            SplashActivity.goToStart(getContext());

        }

    }

    private void setupOpeningHours(final View view) {

            List<OpeningHour> openingHoursList = openingHourContext.loadOpeningHours(venue, new OpeningHourCompletion.OpeningHourErrorCompletion() {

                @Override
                public void completion(List<OpeningHour> openingHoursList, AppError error) {

                    if (openingHoursList != null && !openingHoursList.isEmpty()) {

                       openingHours = openingHoursList;

                    }

                    concealer.setVisibility(View.GONE);

                    populateOpeningHours(view);

                }

            });

        openingHours = openingHoursList;

    }

    private void populateOpeningHours(View view) {

        for(int day = 1; day <= 7; day ++) {

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

        onEdited = (OnEdited) context;
    }

}

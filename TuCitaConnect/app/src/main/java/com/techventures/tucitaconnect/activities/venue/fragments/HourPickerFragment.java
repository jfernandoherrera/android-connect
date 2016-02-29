package com.techventures.tucitaconnect.activities.venue.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.TimePicker;

import com.techventures.tucitaconnect.utils.common.AlertDialogError;

public class HourPickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener{

    Button text;
    private OnHourSelected onHourSelected;
    private int initialHour;
    private int initialMinute;

    public interface OnHourSelected {

        boolean onHourSelected(int hour, int min);

    }

    public void setInitialHour(int initialHour) {

        this.initialHour = initialHour;

    }

    public void setInitialMinute(int initialMinute) {

        this.initialMinute = initialMinute;

    }

    @Override
    public void onAttach(Activity activity) {

        onHourSelected = (OnHourSelected) activity;

        super.onAttach(activity);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final TimePickerDialog dialogFragment = new TimePickerDialog(getActivity(), this, initialHour, initialMinute, false);


        return dialogFragment;

    }

    public void setText(Button text) {

        this.text = text;

    }

    private String formatHour(int hour, int minute) {

        int twelveHoursClock = 12;

        String fine;

        String am = "AM";

        String pm = "PM";

        String ampm;

        if(hour > twelveHoursClock) {

            hour = hour - twelveHoursClock;

            ampm = pm;

        }else {

            ampm = am;

        }

        String min;

        if(minute < 10) {

            min = "0" + minute;

        }else {

            min = String.valueOf(minute);

        }

        fine = hour + ":" + min + " " + ampm;

        return fine;

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        String textValue = (String) text.getText();

        text.setText(formatHour(hourOfDay, minute));

        if(! onHourSelected.onHourSelected(hourOfDay, minute)) {

            text.setText(textValue);

            AlertDialogError alertDialogError = new AlertDialogError();

            alertDialogError.noIsPossible(getContext());

        }

    }
}

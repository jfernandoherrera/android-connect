package com.example.techventures.tucitaconnect.activities.venue.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.TimePicker;

public class HourPickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener{

    Button text;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final TimePickerDialog dialogFragment = new TimePickerDialog(getActivity(), this, 0, 0, false);



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

        text.setText(formatHour(hourOfDay, minute));

    }
}

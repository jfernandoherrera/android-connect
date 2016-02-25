package com.example.techventures.tucitaconnect.activities.venue.fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

public class HourPickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);

        int month = c.get(Calendar.MONTH);

        int day = c.get(Calendar.DAY_OF_MONTH);

        final TimePickerDialog dialogFragment = new TimePickerDialog(getActivity(), this, 0, 0, false);
        return dialogFragment;

    }



    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }
}

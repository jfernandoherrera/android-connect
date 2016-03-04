package com.techventures.tucitaconnect.activities.venue.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.techventures.tucitaconnect.R;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
     implements DatePickerDialog.OnDateSetListener{

    private OnDateSelected listener;
    Calendar calendar;

    public interface OnDateSelected{

        void onDateSelected(int year, int monthOfYear, int dayOfMonth);
    }

    @Override
    public void onAttach(Activity activity) {

        listener = (OnDateSelected) activity;

        super.onAttach(activity);

    }

    public void setCalendar(Calendar calendar) {

        this.calendar = calendar;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int year = calendar.get(Calendar.YEAR);

        int month = calendar.get(Calendar.MONTH);

        int day = calendar.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog dialogFragment = new DatePickerDialog(getActivity(), this, year, month, day);

        DialogInterface.OnClickListener  listeners = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        };

        dialogFragment.setButton(DialogInterface.BUTTON_POSITIVE,getContext().getString(R.string.ok), listeners);

        calendar.add(Calendar.MONTH, 2);

        int daysToEnd = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - calendar.get(Calendar.DAY_OF_MONTH);

        calendar.add(Calendar.DATE, daysToEnd);

        dialogFragment.getDatePicker().setMaxDate(calendar.getTime().getTime());

        return dialogFragment;

    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        listener.onDateSelected(year, monthOfYear, dayOfMonth);
    }

}

package com.techventures.tucitaconnect.activities.venue.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.techventures.tucitaconnect.R;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
     implements DatePickerDialog.OnDateSetListener{

    private OnDateSelected listener;


    public interface OnDateSelected{

        void onDateSelected(int year, int monthOfYear, int dayOfMonth);
    }

    @Override
    public void onAttach(Activity activity) {

        listener = (OnDateSelected) activity;

        super.onAttach(activity);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);

        int month = c.get(Calendar.MONTH);

        int day = c.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog dialogFragment = new DatePickerDialog(getActivity(), this, year, month, day);

        dialogFragment.getDatePicker().setMinDate(c.getTime().getTime());

        DialogInterface.OnClickListener  listeners = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        };

        dialogFragment.setButton(DialogInterface.BUTTON_POSITIVE,getContext().getString(R.string.ok), listeners);

        c.add(Calendar.MONTH, 2);

        int daysToEnd = c.getActualMaximum(Calendar.DAY_OF_MONTH) - c.get(Calendar.DAY_OF_MONTH);

        c.add(Calendar.DATE, daysToEnd);

        dialogFragment.getDatePicker().setMaxDate(c.getTime().getTime());

        return dialogFragment;

    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        listener.onDateSelected(year, monthOfYear, dayOfMonth);
    }

}

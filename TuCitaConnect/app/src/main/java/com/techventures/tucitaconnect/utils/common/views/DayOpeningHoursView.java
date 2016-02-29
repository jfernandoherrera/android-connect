package com.techventures.tucitaconnect.utils.common.views;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.techventures.tucitaconnect.R;
import com.techventures.tucitaconnect.activities.venue.fragments.EditOpeningHoursFragment;

import java.util.Calendar;

public class DayOpeningHoursView extends RelativeLayout{

    private StateButton stateButton;
    private Button textFrom;
    private Button  textTo;
    private TextView day;
    private LinearLayout relativeLayout;
    final int twelveHoursClock = 12;
    final String am = "AM";
    final String pm = "PM";
    private EditOpeningHoursFragment.OnTimeSelected onTimeSelected;


    public DayOpeningHoursView(Context context, AttributeSet attrs) {

        super(context, attrs);

        init(context);

    }

    public boolean hasInitialDateListener() {

        boolean has = onTimeSelected != null;

        return has;

    }

    public void setOnTimeSelected(EditOpeningHoursFragment.OnTimeSelected onTimeSelected) {

        this.onTimeSelected = onTimeSelected;

    }

    public StateButton getStateButton() {

        return stateButton;

    }

    public int getEndHour() {

        String endHour = textTo.getText().toString().split("[:]+|AM|PM")[0];

        int hour =  Integer.parseInt(endHour);

        if(textTo.getText().toString().contains(pm)) {

            hour += 12;

        }

        return hour;

    }

    public int getEndMinute() {


        String minute = textTo.getText().toString().split("[:]+|AM|PM")[1];

        int endMinute;

        try {

            endMinute = Integer.parseInt(minute);

        } catch (NumberFormatException e) {

            endMinute = Integer.parseInt(minute.replaceFirst("0", "").trim());

        }

        return endMinute;
    }

    public int getStartHour() {


        String[] startHour = textFrom.getText().toString().split("[:]+|AM|PM");

        int hour =  Integer.parseInt(startHour[0]);

        if(textFrom.getText().toString().contains(pm)) {

            hour += 12;

        }

        return hour;

    }

    public int getStartMinute() {

        String minute = textFrom.getText().toString().split("[:]+|AM|PM")[1];

        int startMinute;

        try {

            startMinute = Integer.parseInt(minute);

        } catch (NumberFormatException e) {

            startMinute = Integer.parseInt(minute.replaceFirst("0", "").trim());

        }

        return startMinute;

    }

    private void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.item_day_opening_hour, this);

        textFrom = (Button ) findViewById(R.id.open);

        textTo = (Button ) findViewById(R.id.close);

        day = (TextView) findViewById(R.id.textDay);

        stateButton = (StateButton) findViewById(R.id.business);

        relativeLayout = (LinearLayout) findViewById(R.id.concealer);

        textFrom.setText(getResources().getString(R.string.open_default));

        textTo.setText(getResources().getString(R.string.close_default));

        stateButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                clickState();

            }
        });

    }

    private String formatHour(int hour, int minute) {

        String fine;

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

    public void clickState() {

        stateButton.click();

        if(! stateButton.getOpen()) {

            relativeLayout.setVisibility(GONE);

        } else {

            relativeLayout.setVisibility(VISIBLE);

        }

    }

    public void setDay(int day) {

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_WEEK, day);

        String dayString = new java.text.SimpleDateFormat("EEEE").format(calendar.getTime());

        this.day.setText(dayString);

    }

    public void setOpeningHour(int openHour, int openMinute, int closeHour, int closeMinute) {

        textFrom.setText(formatHour(openHour, openMinute));

        textFrom.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                onTimeSelected.onTimeSelected(textTo, getStartHour(), getStartMinute());

                return false;
            }
        });

        textTo.setText(formatHour(closeHour, closeMinute));

        textTo.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                onTimeSelected.onTimeSelected(textFrom, getEndHour(), getEndMinute());

                return false;
            }
        });

    }

}


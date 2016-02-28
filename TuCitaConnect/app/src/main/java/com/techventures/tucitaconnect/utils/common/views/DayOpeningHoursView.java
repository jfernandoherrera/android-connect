package com.techventures.tucitaconnect.utils.common.views;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.techventures.tucitaconnect.R;
import java.util.Calendar;

public class DayOpeningHoursView extends RelativeLayout{

    private StateButton stateButton;
    private Button textFrom;
    private Button  textTo;
    private TextView day;
    private LinearLayout relativeLayout;

    public DayOpeningHoursView(Context context, AttributeSet attrs) {

        super(context, attrs);

        init(context);

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

        textTo.setText(formatHour(closeHour, closeMinute));

    }

}


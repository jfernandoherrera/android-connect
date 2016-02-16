package com.example.techventures.tucitaconnect.utils.common.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.techventures.tucitaconnect.R;


public class AppointmentView extends RelativeLayout {

    private TextView textName;
    private TextView textDate;

    public AppointmentView(Context context, AttributeSet attrs) {

        super(context, attrs);

        init(context);

    }

    private void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.appointment_view, this);

        textName = (TextView) findViewById(R.id.textName);

        textDate = (TextView) findViewById(R.id.textDate);

    }

    public void setTextName(String text) {

        textName.setText(text);

    }

    public void setTypeface(Typeface typeface) {

        textDate.setTypeface(typeface);

        textName.setTypeface(typeface);

    }

    public void setTextDate(String text) {

        textDate.setText(text);

    }

}

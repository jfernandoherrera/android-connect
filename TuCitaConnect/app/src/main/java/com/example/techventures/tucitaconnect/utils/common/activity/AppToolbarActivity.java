package com.example.techventures.tucitaconnect.utils.common.activity;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.example.techventures.tucitaconnect.R;
import com.example.techventures.tucitaconnect.utils.common.AppFont;

import java.lang.reflect.Field;

public class AppToolbarActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private Typeface typeface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        AppFont appFont = new AppFont();

        typeface = appFont.getAppFont(getApplicationContext());

        setToolbar();
    }



    private void setToolbar(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {

            setSupportActionBar(toolbar);

        }

    }

    private TextView getActionBarTextView() {

        TextView titleTextView = null;

        String defaultNameTitleMenu = "mTitleTextView";

        try {

            Field field = toolbar.getClass().getDeclaredField(defaultNameTitleMenu);

            field.setAccessible(true);

            titleTextView = (TextView) field.get(toolbar);

        } catch (NoSuchFieldException e) {

        } catch (IllegalAccessException e) {

        }

        return titleTextView;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        TextView textView = getActionBarTextView();

        textView.setTypeface(typeface, Typeface.BOLD);

        return true;

    }
}

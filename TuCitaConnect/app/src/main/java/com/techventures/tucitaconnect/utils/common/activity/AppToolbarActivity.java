package com.techventures.tucitaconnect.utils.common.activity;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.techventures.tucitaconnect.R;
import com.techventures.tucitaconnect.utils.common.AppFont;

import java.lang.reflect.Field;

public class AppToolbarActivity extends AppCompatActivity{

    protected Toolbar toolbar;
    private Typeface typeface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }



    protected void setToolbar(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {

            setSupportActionBar(toolbar);

        }

    }

    protected TextView getActionBarTextView() {

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

}

package com.techventures.tucitaconnect.utils.common.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.techventures.tucitaconnect.R;
import com.techventures.tucitaconnect.utils.common.views.AppToolbar;

public class AppToolbarActivity extends AppCompatActivity{

    protected AppToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    protected void setToolbar(){

        toolbar = (AppToolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {

            setSupportActionBar(toolbar);

        }

    }

}

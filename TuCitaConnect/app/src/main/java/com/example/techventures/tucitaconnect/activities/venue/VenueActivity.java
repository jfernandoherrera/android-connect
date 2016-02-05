package com.example.techventures.tucitaconnect.activities.venue;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.techventures.tucitaconnect.R;
import com.example.techventures.tucitaconnect.activities.venue.fragments.DayAppointmentsFragment;
import com.example.techventures.tucitaconnect.model.context.slot.SlotCompletion;
import com.example.techventures.tucitaconnect.model.context.slot.SlotContext;
import com.example.techventures.tucitaconnect.model.context.venue.VenueCompletion;
import com.example.techventures.tucitaconnect.model.context.venue.VenueContext;
import com.example.techventures.tucitaconnect.model.domain.slot.Slot;
import com.example.techventures.tucitaconnect.model.domain.venue.Venue;
import com.example.techventures.tucitaconnect.model.error.AppError;
import com.example.techventures.tucitaconnect.utils.common.activity.AppToolbarActivity;
import com.example.techventures.tucitaconnect.utils.common.attributes.CommonAttributes;

import java.util.List;

public class VenueActivity extends AppToolbarActivity{

    private VenueContext venueContext;
    private DayAppointmentsFragment dayAppointmentsFragment;
    private Venue venue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_venue);

        dayAppointmentsFragment = new DayAppointmentsFragment();

        venueContext = VenueContext.context(venueContext);

        setupVenue();

    }

    private void setupVenue(){

        String objectId = getIntent().getStringExtra(CommonAttributes.objectId);

        venue = venueContext.getVenue(objectId, new VenueCompletion.ErrorCompletion() {
            @Override
            public void completion(Venue venue, AppError error) {

                VenueActivity.this.venue = venue;

                dayAppointmentsFragment.setupSlots(venue);

                setDayAppointmentsFragment();

            }
        });

    }


    private void setDayAppointmentsFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.fragmentsContainer, dayAppointmentsFragment);

        transaction.commit();

    }

    private void dayAppointmentsFragmentHide() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.hide(dayAppointmentsFragment);

        transaction.commit();

    }

    private void daysAppointmentFragmentShow() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.show(dayAppointmentsFragment);

        transaction.commit();

    }


    public static void goToVenue(Context context, String objectId) {

        Intent intent = new Intent(context, VenueActivity.class);

        intent.putExtra(CommonAttributes.objectId, objectId);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);

    }
}

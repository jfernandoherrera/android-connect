package com.example.techventures.tucitaconnect.activities.venue;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.techventures.tucitaconnect.R;
import com.example.techventures.tucitaconnect.activities.venue.adapters.SlotsAppointmentsAdapter;
import com.example.techventures.tucitaconnect.activities.venue.adapters.layout.SlotLayoutManager;
import com.example.techventures.tucitaconnect.activities.venue.fragments.DatePickerFragment;
import com.example.techventures.tucitaconnect.model.context.appointment.AppointmentCompletion;
import com.example.techventures.tucitaconnect.model.context.appointment.AppointmentContext;
import com.example.techventures.tucitaconnect.model.context.slot.SlotCompletion;
import com.example.techventures.tucitaconnect.model.context.slot.SlotContext;
import com.example.techventures.tucitaconnect.model.context.venue.VenueCompletion;
import com.example.techventures.tucitaconnect.model.context.venue.VenueContext;
import com.example.techventures.tucitaconnect.model.domain.appointment.Appointment;
import com.example.techventures.tucitaconnect.model.domain.slot.Slot;
import com.example.techventures.tucitaconnect.model.domain.venue.Venue;
import com.example.techventures.tucitaconnect.model.error.AppError;
import com.example.techventures.tucitaconnect.utils.common.AppFont;
import com.example.techventures.tucitaconnect.utils.common.activity.AppToolbarActivity;
import com.example.techventures.tucitaconnect.utils.common.attributes.CommonAttributes;
import com.example.techventures.tucitaconnect.utils.common.scroll.AppHorizontalScrollView;
import com.example.techventures.tucitaconnect.utils.common.scroll.AppScrollView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class VenueActivity extends AppToolbarActivity{

    private VenueContext venueContext;
    private AppointmentContext appointmentContext;
    private Venue venue;
    private float mx, my;
    private SlotContext slotContext;
    private List<Slot> slots;
    private List<Appointment> appointments;
    private TextView noResults;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RelativeLayout concealer;
    private AppScrollView appScrollView;
    private AppHorizontalScrollView appHorizontalScrollView;
    private Typeface typeface;
    private Button button;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_venue);

        setToolbar();

        appointments = new ArrayList<Appointment>();

        slots = new ArrayList<Slot>();

        venueContext = VenueContext.context(venueContext);

        appointmentContext = AppointmentContext.context(appointmentContext);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        appHorizontalScrollView = (AppHorizontalScrollView)findViewById(R.id.scrollHorizontal);

        appScrollView = (AppScrollView) findViewById(R.id.scrollVertical);

        concealer = (RelativeLayout) findViewById(R.id.concealer);

        noResults = (TextView) findViewById(R.id.noResults);

        button = (Button) findViewById(R.id.datePicker);

        calendar = Calendar.getInstance();

        AppFont appFont = new AppFont();

        typeface = appFont.getAppFont(getApplicationContext());

        button.setTypeface(typeface, Typeface.BOLD);

        setupVenue();

        dateFormat();

    }

    private void dateFormat(){

        String title = DateUtils.formatDateTime(getApplicationContext(),

                calendar.getTimeInMillis(),

                DateUtils.FORMAT_SHOW_DATE

                        | DateUtils.FORMAT_SHOW_WEEKDAY

                        | DateUtils.FORMAT_SHOW_YEAR

                        | DateUtils.FORMAT_ABBREV_MONTH

                        | DateUtils.FORMAT_ABBREV_WEEKDAY);

        button.setText(title);

    }

    public void showTimePickerDialog(View v) {

        DialogFragment newFragment = new DatePickerFragment();

        String tag = "timePicker";

        newFragment.show(getSupportFragmentManager(), tag);
    }

    public void setupSlots(final Calendar calendar){

        slotContext = SlotContext.context(slotContext);

        slotContext.loadDaySlots(venue, 6, new SlotCompletion.SlotErrorCompletion() {

            @Override
            public void completion(List<Slot> slotList, AppError error) {

                if (slotList != null && !slotList.isEmpty()) {

                    slots.addAll(slotList);

                    setupAppointments(calendar);
                }

            }
        });

    }

    private void setupAppointments(Calendar date) {

    appointmentContext.loadAppointmentsDateVenue(venue, date, new AppointmentCompletion.AppointmentErrorCompletion() {

        @Override
        public void completion(List<Appointment> appointmentList, AppError error) {


                if (appointmentList != null && !appointmentList.isEmpty()) {

                    appointments.addAll(appointmentList);
                }

            int columns = 0;

            for (Slot slot : slots) {

                slot.setAmount();

                int amount = slot.getAmount();

                if (amount > columns) {

                    columns = amount;

                }

            }

            layoutManager = new SlotLayoutManager(getApplicationContext(), columns);

            recyclerView.setLayoutManager(layoutManager);

            adapter = new SlotsAppointmentsAdapter(slots, typeface, columns);

            recyclerView.setAdapter(adapter);

        }

        @Override
        public void completion(Appointment appointment, AppError error) {

        }
    });

    }

    private void setupVenue() {

        String objectId = getIntent().getStringExtra(CommonAttributes.objectId);

        venue = venueContext.getVenue(objectId, new VenueCompletion.ErrorCompletion() {
            @Override
            public void completion(Venue venue, AppError error) {

                VenueActivity.this.venue = venue;

                setupSlots(Calendar.getInstance());

                TextView textView = getActionBarTextView();

                textView.setText(venue.getName());

            }
        });

    }

        public static void goToVenue(Context context, String objectId) {

        Intent intent = new Intent(context, VenueActivity.class);

        intent.putExtra(CommonAttributes.objectId, objectId);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float curX, curY;

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                mx = event.getX();

                my = event.getY();

                break;

            case MotionEvent.ACTION_MOVE:

                curX = event.getX();

                curY = event.getY();

                appScrollView.scrollBy((int) (mx - curX), (int) (my - curY));

                appHorizontalScrollView.scrollBy((int) (mx - curX), (int) (my - curY));

                mx = curX;

                my = curY;

                break;

            case MotionEvent.ACTION_UP:

                curX = event.getX();

                curY = event.getY();

                appScrollView.scrollBy((int) (mx - curX), (int) (my - curY));

                appHorizontalScrollView.scrollBy((int) (mx - curX), (int) (my - curY));

                break;

        }

        return false;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {



        return true;
    }
}

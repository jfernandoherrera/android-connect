package com.example.techventures.tucitaconnect.activities.venue;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.techventures.tucitaconnect.R;
import com.example.techventures.tucitaconnect.activities.login.LoginActivity;
import com.example.techventures.tucitaconnect.activities.venue.adapters.LeftBarAdapter;
import com.example.techventures.tucitaconnect.activities.venue.adapters.SlotsAppointmentsAdapter;
import com.example.techventures.tucitaconnect.activities.venue.adapters.layout.SlotLayoutManager;
import com.example.techventures.tucitaconnect.activities.venue.fragments.AddUserFragment;
import com.example.techventures.tucitaconnect.activities.venue.fragments.AppointmentDetailsFragment;
import com.example.techventures.tucitaconnect.activities.venue.fragments.DatePickerFragment;
import com.example.techventures.tucitaconnect.model.context.appointment.AppointmentCompletion;
import com.example.techventures.tucitaconnect.model.context.appointment.AppointmentContext;
import com.example.techventures.tucitaconnect.model.context.service.ServiceCompletion;
import com.example.techventures.tucitaconnect.model.context.service.ServiceContext;
import com.example.techventures.tucitaconnect.model.context.slot.SlotCompletion;
import com.example.techventures.tucitaconnect.model.context.slot.SlotContext;
import com.example.techventures.tucitaconnect.model.context.venue.VenueCompletion;
import com.example.techventures.tucitaconnect.model.context.venue.VenueContext;
import com.example.techventures.tucitaconnect.model.domain.appointment.Appointment;
import com.example.techventures.tucitaconnect.model.domain.service.Service;
import com.example.techventures.tucitaconnect.model.domain.slot.Slot;
import com.example.techventures.tucitaconnect.model.domain.venue.Venue;
import com.example.techventures.tucitaconnect.model.error.AppError;
import com.example.techventures.tucitaconnect.utils.common.AppFont;
import com.example.techventures.tucitaconnect.utils.common.ResponsiveInteraction;
import com.example.techventures.tucitaconnect.utils.common.activity.AppToolbarActivity;
import com.example.techventures.tucitaconnect.utils.common.attributes.CommonAttributes;
import com.example.techventures.tucitaconnect.utils.common.scroll.AppHorizontalScrollView;
import com.example.techventures.tucitaconnect.utils.common.scroll.AppScrollView;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class VenueActivity extends AppToolbarActivity implements DatePickerFragment.OnDateSelected, SlotsAppointmentsAdapter.OnTouchToClick{

    private VenueContext venueContext;
    private AppointmentContext appointmentContext;
    private Venue venue;
    private float mx, my;
    private SlotContext slotContext;
    private TextView noResults;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView leftBarRecyclerView;
    private RecyclerView.Adapter leftBarAdapter;
    private RecyclerView.LayoutManager leftBarLayoutManager;
    private RelativeLayout concealer;
    private AppScrollView appScrollView;
    private AppHorizontalScrollView appHorizontalScrollView;
    private Typeface typeface;
    private Button button;
    private Calendar calendar;
    private double dimension;
    private Appointment appointment;
    private Slot slot;
    private TextView appointmentFloatingView;
    private ServiceContext serviceContext;
    float downX = 0, downY = 0;
    int twoDigitsNumber = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_venue);

        setToolbar();

        venueContext = VenueContext.context(venueContext);

        serviceContext = ServiceContext.context(serviceContext);

        appointmentContext = AppointmentContext.context(appointmentContext);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        leftBarRecyclerView = (RecyclerView) findViewById(R.id.recycler_viewLeftBar);

        appHorizontalScrollView = (AppHorizontalScrollView)findViewById(R.id.scrollHorizontal);

        appScrollView = (AppScrollView) findViewById(R.id.scrollVertical);

        concealer = (RelativeLayout) findViewById(R.id.concealer);

        noResults = (TextView) findViewById(R.id.noResults);

        button = (Button) findViewById(R.id.datePicker);

        appointmentFloatingView = (TextView) findViewById(R.id.appointmentView);

        appointmentFloatingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showAppointmentDialog(appointment);

            }
        });

        calendar = Calendar.getInstance();

        AppFont appFont = new AppFont();

        typeface = appFont.getAppFont(getApplicationContext());

        button.setTypeface(typeface, Typeface.BOLD);

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    v.setBackgroundResource(ResponsiveInteraction.getPressedButton());

                } else if (event.getAction() == MotionEvent.ACTION_UP) {

                    v.setBackgroundResource(ResponsiveInteraction.getNormalButton());

                }

                return false;
            }
        });

        setupVenue();

        dateFormat(calendar);

        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics metrics = new DisplayMetrics();

        wm.getDefaultDisplay().getMetrics(metrics);

        dimension = metrics.scaledDensity;

    }

    private void dateFormat(Calendar calendar){

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

    public void showAppointmentDialog(Appointment appointment) {

        AppointmentDetailsFragment newFragment = new AppointmentDetailsFragment();

        newFragment.setAppointment(appointment);

        setupServices(newFragment, appointment);

    }

    public void showAddUserDialog() {

        AddUserFragment newFragment = new AddUserFragment();

        newFragment.setVenue(venue);

        final String tag = "AddUser";

        newFragment.show(getSupportFragmentManager(), tag);

    }




    private void setupServices(final AppointmentDetailsFragment appointmentDetailsFragment , final Appointment appointment){

        final String tag = "AppointmentDetails";

        serviceContext.loadAppointmentServices(appointment, new ServiceCompletion.ErrorCompletion() {

            @Override
            public void completion(List<Service> servicesList, AppError error) {

                if (servicesList != null) {

                    appointmentDetailsFragment.setServices(servicesList);


                    if (!servicesList.isEmpty()) {

                        appointmentDetailsFragment.show(getSupportFragmentManager(), tag);

                    }

                }

            }
        });

    }

    public void setupSlots(final Calendar calendar){

        concealer.setVisibility(View.VISIBLE);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        slotContext = SlotContext.context(slotContext);

        slotContext.loadDaySlots(venue, dayOfWeek, new SlotCompletion.SlotErrorCompletion() {

            @Override
            public void completion(List<Slot> slotList, AppError error) {

                TextView closed = (TextView) findViewById(R.id.closed);

                if (slotList != null && !slotList.isEmpty()) {

                    setupAppointments(calendar, slotList);

                    appScrollView.setVisibility(View.VISIBLE);

                    closed.setVisibility(View.GONE);

                    leftBarRecyclerView.setVisibility(View.VISIBLE);

                } else {

                    leftBarRecyclerView.setVisibility(View.GONE);

                    closed.setVisibility(View.VISIBLE);

                    appScrollView.setVisibility(View.GONE);

                    concealer.setVisibility(View.GONE);

                }

            }
        });

    }

    private void setupAppointments(final Calendar date, final List<Slot> slots) {


    appointmentContext.loadAppointmentsDateVenue(venue, date, new AppointmentCompletion.AppointmentErrorCompletion() {

        @Override
        public void completion(List<Appointment> appointmentList, AppError error) {

            int columns = 0;

            for (Slot slot : slots) {

                slot.setAmount();

                int amount = slot.getAmount();

                if (amount > columns) {

                    columns = amount;

                }

            }

            if (appointmentList != null && !appointmentList.isEmpty()) {


            }

            if (layoutManager == null) {

                layoutManager = new SlotLayoutManager(getApplicationContext(), columns, slots.size());

                recyclerView.setLayoutManager(layoutManager);

                leftBarLayoutManager = new LinearLayoutManager(getApplicationContext());

                leftBarRecyclerView.setLayoutManager(leftBarLayoutManager);

            } else {

                ((SlotLayoutManager) layoutManager).setCols(columns);

                ((SlotLayoutManager) layoutManager).setNumSlots(slots.size());

            }

            if (adapter == null) {

                adapter = new SlotsAppointmentsAdapter(slots, typeface, columns, appointmentList, VenueActivity.this);

                recyclerView.setAdapter(adapter);

                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.HOUR_OF_DAY, slots.get(0).getStartHour());

                calendar.set(Calendar.MINUTE, slots.get(0).getStartMinute());

                calendar.set(Calendar.SECOND, 0);

                leftBarAdapter = new LeftBarAdapter(slots.get(0).getDurationMinutes(), slots.size(), calendar);

                leftBarRecyclerView.setAdapter(leftBarAdapter);

            } else {

                ((SlotsAppointmentsAdapter) adapter).setAmount(columns);

                ((SlotsAppointmentsAdapter) adapter).setAppointments(appointmentList);

                ((SlotsAppointmentsAdapter) adapter).setSlots(slots);

                ((LeftBarAdapter) leftBarAdapter).setAmount(slots.size());

                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.HOUR_OF_DAY, slots.get(0).getStartHour());

                calendar.set(Calendar.MINUTE, slots.get(0).getStartMinute());

                calendar.set(Calendar.SECOND, 0);

                ((LeftBarAdapter) leftBarAdapter).setInitialDate(calendar);
            }

            concealer.setVisibility(View.GONE);

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

    public void addOneDay(View view){

        calendar.add(Calendar.DATE, 1);

        setupSlots(calendar);

        dateFormat(calendar);

        appScrollView.scrollTo(0, 0);

        leftBarRecyclerView.scrollToPosition(0);

    }

    public void removeOneDay(View v){

        calendar.add(Calendar.DATE, -1);

        setupSlots(calendar);

        dateFormat(calendar);

        appScrollView.scrollTo(0, 0);

        leftBarRecyclerView.scrollToPosition(0);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float curX, curY;

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                mx = event.getX();

                my = event.getY();

                downX = mx;

                downY = my;

                break;

            case MotionEvent.ACTION_MOVE:

                    curX = event.getX();

                    curY = event.getY();

                    appScrollView.scrollBy((int) (mx - curX), (int) (my - curY));

                    leftBarRecyclerView.scrollBy((int) (mx - curX), (int) (my - curY));

                    appHorizontalScrollView.scrollBy((int) (mx - curX), (int) (my - curY));

                    my = curY;

                    mx = curX;

                break;

            case MotionEvent.ACTION_UP:

                curX = event.getX();

                curY = event.getY();

                appScrollView.scrollBy((int) (mx - curX), (int) (my - curY));

                leftBarRecyclerView.scrollBy((int) (mx - curX), (int) (my - curY));

                appHorizontalScrollView.scrollBy((int) (mx - curX), (int) (my - curY));

                boolean equalX = curX == downX;

                boolean equalY = curY == downY;

                if(equalX && equalY && appointment != null){

                    showAppointmentDialog(appointment);

                }

                break;

        }

        return true;

    }


    @Override
    public void onDateSelected(int year, int monthOfYear, int dayOfMonth) {

        calendar.set(year, monthOfYear, dayOfMonth);

        setupSlots(calendar);

        dateFormat(calendar);

        appScrollView.scrollTo(0, 0);

        leftBarRecyclerView.scrollToPosition(0);

    }

    private void setupFloatButton(){

        if(appointment == null) {

            setupJustSlot();

        }else {

            setupWithAppointment();

        }

    }

    private void setupWithAppointment(){

        Date date = appointment.getDate();

        int hour = date.getHours();

        int minute = date.getMinutes();

        String startHour = setupSpacesTime(hour, minute);

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, hour);

        calendar.set(Calendar.MINUTE, minute);

        int[] duration = appointment.getDuration();

        calendar.add(Calendar.HOUR_OF_DAY, duration[0]);

        calendar.add(Calendar.MINUTE, duration[1]);

        date = calendar.getTime();

        hour = date.getHours();

        minute = date.getMinutes();

        String endHour = setupSpacesTime(hour, minute);

        String text = startHour + "\n" + endHour;

        appointmentFloatingView.setText(text);

    }

    private void setupJustSlot(){

        String time = setupSpacesTime(slot.getStartHour(), slot.getStartMinute());

        String preMinute =  slot.getDurationMinutes() + " min";

        String text = time + "\n" + preMinute;

        appointmentFloatingView.setText(text);

    }

    private String setupSpacesTime(int hour, int minute) {

        String text;

        if(minute < twoDigitsNumber){

            text = setupSpacesHour(hour) + ":0" + minute;

        }else {

            text = setupSpacesHour(hour) + ":" + minute;

        }

        return text;

    }

    private String setupSpacesHour(int hour){

        String hourString;

        if(hour < twoDigitsNumber) {

            hourString = " " + hour;

        }else {

            hourString = "" + hour;
        }

        return hourString;
    }



    @Override
    public void onTouchToClick(int x, int y, Slot slot, int column) {

        this.slot = slot;

        Appointment appointment = slot.getAppointment(column);

        if(appointment != null) {

            this.appointment = appointment;

        }else {

            this.appointment = null;

        }

        setupFloatButton();

    }
}

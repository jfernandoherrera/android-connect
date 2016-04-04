package com.techventures.tucitaconnect.activities.venue;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.format.DateUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.techventures.tucitaconnect.R;
import com.techventures.tucitaconnect.activities.splash.SplashActivity;
import com.techventures.tucitaconnect.activities.venue.adapters.LeftBarAdapter;
import com.techventures.tucitaconnect.activities.venue.adapters.DiaryAdapter;
import com.techventures.tucitaconnect.activities.venue.adapters.layout.SlotLayoutManager;
import com.techventures.tucitaconnect.activities.venue.fragments.AddUserFragment;
import com.techventures.tucitaconnect.activities.venue.fragments.AppointmentDetailsFragment;
import com.techventures.tucitaconnect.activities.venue.fragments.DatePickerFragment;
import com.techventures.tucitaconnect.activities.venue.fragments.editopeninghour.EditOpeningHoursFragment;
import com.techventures.tucitaconnect.activities.venue.fragments.HourPickerFragment;
import com.techventures.tucitaconnect.activities.venue.fragments.SelectTimesToBlockFragment;
import com.techventures.tucitaconnect.model.context.appointment.AppointmentCompletion;
import com.techventures.tucitaconnect.model.context.appointment.AppointmentContext;
import com.techventures.tucitaconnect.model.context.blockade.BlockadeCompletion;
import com.techventures.tucitaconnect.model.context.blockade.BlockadeContext;
import com.techventures.tucitaconnect.model.context.service.ServiceCompletion;
import com.techventures.tucitaconnect.model.context.service.ServiceContext;
import com.techventures.tucitaconnect.model.context.slot.SlotCompletion;
import com.techventures.tucitaconnect.model.context.slot.SlotContext;
import com.techventures.tucitaconnect.model.context.venue.VenueCompletion;
import com.techventures.tucitaconnect.model.context.venue.VenueContext;
import com.techventures.tucitaconnect.model.domain.appointment.Appointment;
import com.techventures.tucitaconnect.model.domain.blockade.Blockade;
import com.techventures.tucitaconnect.model.domain.blockade.BlockadeAttributes;
import com.techventures.tucitaconnect.model.domain.service.Service;
import com.techventures.tucitaconnect.model.domain.slot.Slot;
import com.techventures.tucitaconnect.model.domain.venue.Venue;
import com.techventures.tucitaconnect.model.error.AppError;
import com.techventures.tucitaconnect.utils.common.AppFont;
import com.techventures.tucitaconnect.utils.common.CustomSpanTypeface;
import com.techventures.tucitaconnect.utils.common.ResponsiveInteraction;
import com.techventures.tucitaconnect.utils.common.activity.AppToolbarActivity;
import com.techventures.tucitaconnect.utils.common.attributes.CommonAttributes;
import com.techventures.tucitaconnect.utils.common.scroll.AppHorizontalScrollView;
import com.techventures.tucitaconnect.utils.common.scroll.AppScrollView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.techventures.tucitaconnect.utils.common.scroll.LinearLayoutManager;
import com.techventures.tucitaconnect.utils.common.views.AppTextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class VenueActivity extends AppToolbarActivity implements EditOpeningHoursFragment.OnEdited, GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener, EditOpeningHoursFragment.OnTimeSelected, DatePickerFragment.OnDateSelected, DiaryAdapter.OnTouchToClick, SelectTimesToBlockFragment.OnJustOneDateSelected, HourPickerFragment.OnHourSelected{

    private VenueContext venueContext;
    private AppointmentContext appointmentContext;
    private Venue venue;
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
    private AppScrollView leftAppScrollView;
    private AppHorizontalScrollView appHorizontalScrollView;
    private AppTextView selectDate;
    private Calendar calendar;
    private Calendar currentDay;
    private Appointment appointment;
    private final int swipeThresholdVelocityInsensitive = 110;
    private final int swipeThresholdVelocity = 3500;
    private Slot slot;
    private RelativeLayout progress;
    private AppTextView appointmentFloatingView;
    private ServiceContext serviceContext;
    int twoDigitsNumber = 10;
    private SelectTimesToBlockFragment selectTimesToBlockFragment;
    private int column;
    private BlockadeContext blockadeContext;
    private Button isTo;
    private int ultimateIsToHour, ultimateIsToMinute, ultimateIsFromHour, ultimateIsFromMinute;
    private Button isFrom;
    private GestureDetectorCompat mDetector;
    float isInsensitive;
    private boolean allDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_venue);

        setToolbar();

        isInsensitive = 0;

        venueContext = VenueContext.context(venueContext);

        currentDay = Calendar.getInstance();

        mDetector = new GestureDetectorCompat(this,this);

        blockadeContext = BlockadeContext.context(blockadeContext);

        serviceContext = ServiceContext.context(serviceContext);

        appointmentContext = AppointmentContext.context(appointmentContext);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        leftBarRecyclerView = (RecyclerView) findViewById(R.id.recycler_viewLeftBar);

        appHorizontalScrollView = (AppHorizontalScrollView)findViewById(R.id.scrollHorizontal);

        appScrollView = (AppScrollView) findViewById(R.id.scrollVertical);

        leftAppScrollView = (AppScrollView) findViewById(R.id.leftBarContainer);

        concealer = (RelativeLayout) findViewById(R.id.concealer);

        noResults = (TextView) findViewById(R.id.noResults);

        selectDate = (AppTextView) findViewById(R.id.datePicker);

        progress = (RelativeLayout) findViewById(R.id.progress);

        progress.setVisibility(View.GONE);

        appointmentFloatingView = (AppTextView) findViewById(R.id.appointmentOptionsView);

        appointmentFloatingView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(VenueActivity.this, v);

                Menu menu = popupMenu.getMenu();

                String actionMultipleBlock = getString(R.string.action_multiple_block);

                menu.add(typefaceAction(actionMultipleBlock)).setAlphabeticShortcut('c');

                String actionEdit = getString(R.string.action_edit_working_hours);

                menu.add(typefaceAction(actionEdit)).setAlphabeticShortcut('d');

                if(slot != null){

                    String actionBlock = getString(R.string.action_block);

                    menu.add(typefaceAction(actionBlock)).setAlphabeticShortcut('a');

                    String actionChangeAmount = getString(R.string.action_change_amount);

                    menu.add(typefaceAction(actionChangeAmount)).setAlphabeticShortcut('b');

                }

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if(item.getAlphabeticShortcut() == 'a'){

                            actionBlock();

                        } else if(item.getAlphabeticShortcut() == 'c') {

                           showSelectTimesDialog();

                        } else if(item.getTitle().equals(getString(R.string.action_add_appointment))) {



                        } else if(item.getAlphabeticShortcut() == 'd') {

                               showEditOpeningHoursDialog();

                        } else if(item.getAlphabeticShortcut() == 'b') {

                          changeAvailability();

                        }

                        return false;

                    }

                });

                popupMenu.show();

            }

        });

        calendar = Calendar.getInstance();

        setupVenue();

        dateFormat(calendar);

    }

    private SpannableStringBuilder typefaceAction(String action) {

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(action);

        Typeface typeface = new AppFont().getAppFontLight(VenueActivity.this);

        stringBuilder.setSpan(new CustomSpanTypeface(null, Typeface.BOLD, 20, null, null, typeface), 0, action.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        return stringBuilder;

    }

    private void changeAvailability() {

        LayoutInflater layoutInflater = LayoutInflater.from(VenueActivity.this);

        View promptView = layoutInflater.inflate(R.layout.input_number, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VenueActivity.this);

        alertDialogBuilder.setView(promptView);

        this.allDay = true;

        final EditText editText = (EditText) promptView.findViewById(R.id.inputAmount);

        final TextView allDay = (TextView) promptView.findViewById(R.id.allDay);

        final TextView currentSlot = (TextView) promptView.findViewById(R.id.currentSlot);

        allDay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                allDay.setBackgroundResource(R.drawable.button_accent3);

                currentSlot.setBackgroundResource(R.drawable.button_accent2);

                VenueActivity.this.allDay = true;

            }
        });

        currentSlot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                currentSlot.setBackgroundResource(R.drawable.button_accent3);

                allDay.setBackgroundResource(R.drawable.button_accent2);

                VenueActivity.this.allDay = false;

            }
        });

        alertDialogBuilder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                try {

                    int amount = Integer.parseInt(editText.getText().toString());

                    if (VenueActivity.this.allDay) {

                        setSlotAmount(((DiaryAdapter) adapter).getSlots(), amount);

                    } else {

                        setSlotAmount(slot, amount);

                    }

                } catch (NumberFormatException e) {

                }

            }

        })

                .setNegativeButton(getString(R.string.cancel),

                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();

                            }

                        });

        AlertDialog alert = alertDialogBuilder.create();

        alert.show();

    }

    private void setSlotAmount(Slot slot, int amount) {

        slotContext.setAmount(slot, amount, new SlotCompletion.SlotErrorCompletion() {

            @Override
            public void completion(List<Slot> slotList, AppError error) {

                if(error != null) {



                } else {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VenueActivity.this);

                    String continueString = getString(R.string.continue_option);

                    String successful = getString(R.string.successful_transaction);

                    String message = getString(R.string.app_name);

                    alertDialogBuilder.setTitle(successful)

                            .setPositiveButton(continueString, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int id) {

                                   setupSlots(calendar, null);

                                }
                            }).setCancelable(false)

                            .setMessage(message).show();

                }

            }

            @Override
            public void completion(int duration, AppError error) {

            }

        });

    }

    private void setSlotAmount(List<Slot> slot, int amount) {

        slotContext.setAmount(slot, amount, new SlotCompletion.SlotErrorCompletion() {
            @Override
            public void completion(List<Slot> slotList, AppError error) {

                if(error != null) {



                } else {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VenueActivity.this);

                    String continueString = getString(R.string.continue_option);

                    String successful = getString(R.string.successful_transaction);

                    String message = getString(R.string.app_name);

                    alertDialogBuilder.setTitle(successful)

                            .setPositiveButton(continueString, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int id) {

                                    setupSlots(calendar, null);

                                }
                            }).setCancelable(false)

                            .setMessage(message).show();

                }

            }

            @Override
            public void completion(int duration, AppError error) {

            }

        });
    }

    private void actionBlock() {

        String day = new java.text.SimpleDateFormat("EEEE/d/MMMM/yyyy").format(calendar.getTime());

        String from = getString(R.string.from) + " " + formatHour(slot.getStartHour(), slot.getStartMinute());

        String to = getString(R.string.to) + " " + formatHour(slot.getEndHour(), slot.getEndMinute());

        String willBlock = getString(R.string.will_block_the_day) + " " +  day + " " + from + " " + to;

        String title = getString(R.string.do_you_really);

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(willBlock);

        Typeface typeface = new AppFont().getAppFontLight(this);

        stringBuilder.setSpan(new CustomSpanTypeface(null, Typeface.BOLD, 20, null, null, typeface), 0, willBlock.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        SpannableStringBuilder stringBuilderTitle = new SpannableStringBuilder(title);

        stringBuilderTitle.setSpan(new CustomSpanTypeface(null, Typeface.BOLD, 26, null, null, typeface), 0, title.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        new AlertDialog.Builder(VenueActivity.this)

                .setTitle(stringBuilderTitle)

                .setMessage(stringBuilder)

                .setIcon(android.R.drawable.ic_dialog_alert)

                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        blockSlot();

                    }
                })

                .setNegativeButton(android.R.string.no, null).show();

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

    private void blockSlot() {

        Blockade blockade = new Blockade();

        blockade.putDate(calendar.getTime());

        blockade.putType(BlockadeAttributes.typeSlots);

        List<String> strings = new ArrayList<String>();

        strings.add(slot.getObjectId());

        blockade.putDataArray(strings);

        blockadeContext.saveBlockade(blockade, venue, new BlockadeCompletion.ErrorCompletion() {

            @Override
            public void completion(List<Blockade> blockadeList, AppError error) {

                if (error == null) {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(

                            VenueActivity.this);

                    String continueString = getString(R.string.continue_option);

                    String successful = getString(R.string.successful_transaction);

                    String message = getString(R.string.app_name);

                    alertDialogBuilder.setTitle(successful)

                            .setPositiveButton(continueString, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int id) {

                                    SplashActivity.goToStart(getApplicationContext());

                                }
                            }).setCancelable(false)

                            .setMessage(message).show();

                }

            }

        });

    }

    private void dateFormat(Calendar calendar){

        String title = DateUtils.formatDateTime(getApplicationContext(),

                calendar.getTimeInMillis(),

                DateUtils.FORMAT_SHOW_DATE

                        | DateUtils.FORMAT_SHOW_WEEKDAY

                        | DateUtils.FORMAT_SHOW_YEAR

                        | DateUtils.FORMAT_ABBREV_MONTH

                        | DateUtils.FORMAT_ABBREV_WEEKDAY);

        selectDate.setText(title);

    }

    public void showTimePickerDialog(View v) {

        DatePickerFragment newFragment = new DatePickerFragment();

        String tag = "timePicker";

        newFragment.setCalendar(calendar);

        newFragment.show(getSupportFragmentManager(), tag);

    }
    
    public void showHourPickerDialog(View v) {

        HourPickerFragment newFragment = new HourPickerFragment();

        if(isTo != null) {

            newFragment.setInitialHour(ultimateIsFromHour);

            newFragment.setInitialMinute(ultimateIsFromMinute);

            } else {

            newFragment.setInitialHour(ultimateIsToHour);

            newFragment.setInitialMinute(ultimateIsToMinute);

        }

        newFragment.setText((Button) v);

        String tag = "hourPicker";

        newFragment.show(getSupportFragmentManager(), tag);

    }

    public void showEditOpeningHoursDialog() {

        EditOpeningHoursFragment editOpeningHoursFragment = new EditOpeningHoursFragment();

        editOpeningHoursFragment.setVenue(venue);

        String tag = "editOpeningHours";

        editOpeningHoursFragment.show(getSupportFragmentManager(), tag);

    }

    public void showAppointmentDialog(Appointment appointment) {

        progress.setVisibility(View.VISIBLE);

        AppointmentDetailsFragment newFragment = new AppointmentDetailsFragment();

        newFragment.setAppointment(appointment);

        setupServices(newFragment, appointment);

    }

    public void showSelectTimesDialog() {

        //progress.setVisibility(View.VISIBLE);

        selectTimesToBlockFragment = new SelectTimesToBlockFragment();

        String tag = "selectTimes";

        selectTimesToBlockFragment.show(getSupportFragmentManager(), tag);

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

                progress.setVisibility(View.GONE);

            }
        });

    }

    private boolean sameDay(Calendar calendar, Calendar otherCalendar) {

        boolean isSameDay = calendar.get(Calendar.DAY_OF_YEAR) == otherCalendar.get(Calendar.DAY_OF_YEAR);

        boolean isSameYear = calendar.get(Calendar.YEAR) == otherCalendar.get(Calendar.YEAR);

        boolean sameDay = isSameDay && isSameYear;

        return sameDay;
    }

    public void setupSlots(final Calendar calendar, final CalendarDay calendarDay){

        Calendar current = Calendar.getInstance();

        if(calendar != null ) {

            if(sameDay(calendar, current)) {

                appointmentFloatingView.setVisibility(View.VISIBLE);

            } else if(current.after(calendar) ) {

                appointmentFloatingView.setVisibility(View.GONE);

            } else {

                appointmentFloatingView.setVisibility(View.VISIBLE);

            }

        } else if(calendarDay != null) {

            if(sameDay(calendarDay.getCalendar(), current)) {

                appointmentFloatingView.setVisibility(View.VISIBLE);

            } else if (current.after(calendarDay.getCalendar())) {

                appointmentFloatingView.setVisibility(View.GONE);

            } else {

                appointmentFloatingView.setVisibility(View.VISIBLE);

            }

        }

        concealer.setVisibility(View.VISIBLE);

        int dayOfWeek = 0;

        if(calendarDay == null) {

            dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        }else {

            dayOfWeek = calendarDay.getCalendar().get(Calendar.DAY_OF_WEEK);

        }

        slotContext = SlotContext.context(slotContext);

        slotContext.loadDaySlots(venue, dayOfWeek, new SlotCompletion.SlotErrorCompletion() {

            @Override
            public void completion(List<Slot> slotList, AppError error) {

                if (calendarDay != null) {

                    if (slotList != null) {

                        selectTimesToBlockFragment.setSlots(slotList);

                    }

                } else {

                    TextView closed = (TextView) findViewById(R.id.closed);

                    if (slotList != null && !slotList.isEmpty()) {

                        setupAppointments(calendar, slotList);

                        appScrollView.setVisibility(View.VISIBLE);

                        closed.setVisibility(View.GONE);

                    } else {

                        closed.setVisibility(View.VISIBLE);

                        appScrollView.setVisibility(View.GONE);

                    }

                }

                concealer.setVisibility(View.GONE);

            }

            @Override
            public void completion(int duration, AppError error) {

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

                adapter = new DiaryAdapter(slots, columns, appointmentList, VenueActivity.this, null);

                recyclerView.setAdapter(adapter);

                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.HOUR_OF_DAY, slots.get(0).getStartHour());

                calendar.set(Calendar.MINUTE, slots.get(0).getStartMinute());

                calendar.set(Calendar.SECOND, 0);

                leftBarAdapter = new LeftBarAdapter(slots.get(0).getDurationMinutes(), slots.size(), calendar);

                leftBarRecyclerView.setAdapter(leftBarAdapter);

            } else {

                ((DiaryAdapter) adapter).setAmount(columns);

                ((DiaryAdapter) adapter).setAppointments(appointmentList);

                ((DiaryAdapter) adapter).setSlots(slots);

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

        try {

            String objectId = getIntent().getStringExtra(CommonAttributes.objectId);

            venue = venueContext.getVenue(objectId, new VenueCompletion.ErrorCompletion() {

            @Override
            public void completion(Venue venue, AppError error) {

                VenueActivity.this.venue = venue;

                setupSlots(Calendar.getInstance(), null);

                toolbar.setTitle(venue.getName());

            }
        });

        }catch (NullPointerException e) {

            SplashActivity.goToStart(getApplicationContext());

        }

    }

        public static void goToVenue(Context context, String objectId) {

        Intent intent = new Intent(context, VenueActivity.class);

        intent.putExtra(CommonAttributes.objectId, objectId);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);

    }

    public void addOneDay(View view){

        calendar.add(Calendar.DATE, 1);

        setupSlots(calendar, null);

        dateFormat(calendar);

        appScrollView.scrollTo(0, 0);

        leftAppScrollView.scrollTo(0,0);

    }

    public void removeOneDay(View v){

            calendar.add(Calendar.DATE, -1);

            setupSlots(calendar, null);

            dateFormat(calendar);

            appScrollView.scrollTo(0, 0);

            leftAppScrollView.scrollTo(0,0);

    }


    @Override
    public void onDateSelected(int year, int monthOfYear, int dayOfMonth) {

        calendar.set(year, monthOfYear, dayOfMonth);

        setupSlots(calendar, null);

        dateFormat(calendar);

        appScrollView.scrollTo(0, 0);

        leftAppScrollView.scrollTo(0, 0);

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

        String minuteSuffix = "min";

        String preMinute =  slot.getDurationMinutes() + " " + minuteSuffix;

        String text = time + "\n" + preMinute;

        appointmentFloatingView.setText(text);

    }

    private String setupSpacesTime(int hour, int minute) {

        String text;

        int twelveHoursClock = 12;

        String am = "AM";

        String pm = "PM";

        String ampm;

        if(hour > twelveHoursClock) {

            hour = hour - twelveHoursClock;

            ampm = pm;

        }else {

            ampm = am;

        }

        if(minute < twoDigitsNumber){

            text = setupSpacesHour(hour) + ":0" + minute + " " + ampm;

        }else {

            text = setupSpacesHour(hour) + ":" + minute + " " + ampm;

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
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTouchToClick(int x, int y, Slot slot, int column) {

        this.column = column;

        this.slot = slot;

        this.appointment = slot.getAppointment(column);

        setupFloatButton();

    }

    @Override
    public void onJustOneDateSelected(CalendarDay day) {

        setupSlots(null, day);

    }

    public void blocked(View view){

        selectTimesToBlockFragment.blockades(venue);

    }

    @Override
    public void onTimeSelected(Button view, int hour, int minute) {

        if(view.getId() == R.id.open) {

            isFrom = view;

            ultimateIsToHour = hour;

            ultimateIsToMinute = minute;

            isTo = null;

        } else {

            ultimateIsFromMinute = minute;

            ultimateIsFromHour = hour;

            isTo = view;

            isFrom = null;

        }

    }

    @Override
    public boolean onHourSelected(int hour, int min) {

        boolean isGreater;

        if(isTo == null) {

            isGreater = hour > getStartHour(isFrom) || (hour == getStartHour(isFrom) && min > getStartMinute(isFrom));

        } else {

            isGreater = hour < getStartHour(isTo) || (hour == getStartHour(isTo) && min < getStartMinute(isTo));


        }

        return isGreater;

    }

    public int getStartHour(Button button) {

        final String pm = "PM";

        int hour = 0;

            String[] startHour = button.getText().toString().split("[:]+|AM|PM");

            hour = Integer.parseInt(startHour[0]);

            if (button.getText().toString().contains(pm)) {

                hour += 12;

            }

        return hour;

    }


    public int getStartMinute(Button button) {

        int startMinute = 0;

        String minute;

            minute = button.getText().toString().split("[:]+|AM|PM")[1];

            try {

                startMinute = Integer.parseInt(minute);

            } catch (NumberFormatException e) {

                startMinute = Integer.parseInt(minute.replaceFirst("0", "").trim());

            }

        return startMinute;

    }

    @Override
    public boolean onDown(MotionEvent e) {

        int x = appHorizontalScrollView.getScrollX();

        int y = leftAppScrollView.getScrollY();

        appScrollView.smoothScrollTo(x, y);

        leftAppScrollView.smoothScrollTo(x, y);

        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {

        if(adapter != null && slot != null) {

            ((DiaryAdapter) adapter).setSelected(slot);

        }

        return false;

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        leftAppScrollView.scrollBy(0, (int) distanceY);

        appScrollView.scrollBy((int) distanceX, (int) distanceY);

        appHorizontalScrollView.scrollBy((int) distanceX, (int) distanceY);

        return false;

    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        if(isInsensitive == 1) {

            if( Math.abs(velocityY) > swipeThresholdVelocityInsensitive ) {

                velocityY = getInsensitiveVelocity((int) velocityY);

                appScrollView.fling((int) - velocityY);

                leftAppScrollView.fling((int) - velocityY);

            }

        } else if (Math.abs(velocityY) > swipeThresholdVelocity  ) {

                appScrollView.fling((int) - velocityY);

                leftAppScrollView.fling((int) - velocityY);

        }


        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(isInsensitive == 0) {

            isInsensitive = event.getPressure();

        }

        if(this.mDetector != null) {

            mDetector.onTouchEvent(event);

        }

        return false;

    }

    public static boolean isBetween(int x, int lower, int upper) {

        return lower <= x && x <= upper;

    }

    private int getInsensitiveVelocity(int slow) {

        int velocity = slow;

        if(slow < 6000) {

            if(isBetween(slow, 5000, 6000)) {

                velocity = (int) (velocity * 1.4);

            } else if(isBetween(slow, 4000, 5000)) {

                velocity = (int) (velocity * 1.7);

            } else  if(isBetween(slow, 3000, 4000)) {

                velocity = (int) (velocity * 2.3);

            } else if(isBetween(slow, 2000, 3000)) {

                velocity = (int) (velocity * 2.9);

            } else if(isBetween(slow, 1000, 2000)) {

                velocity = (int) (velocity * 3.2);

            } else if(isBetween(slow, 500, 1000)) {

                velocity = (int) (velocity * 3.7);

            } else if(isBetween(slow, 300, 500)) {

                velocity = (velocity * 6);

            } else if(isBetween(slow, 100, 300)) {

                velocity = (velocity * 10);

            }

        }

        return velocity;

    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {

        return false;

    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {

        if(appointment != null) {

            showAppointmentDialog(appointment);

        }

        return false;

    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {

        return false;

    }

    @Override
    public void onEdited() {

        setupVenue();

    }

}

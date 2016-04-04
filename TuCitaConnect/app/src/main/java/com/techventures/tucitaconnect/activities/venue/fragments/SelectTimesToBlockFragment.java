package com.techventures.tucitaconnect.activities.venue.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.techventures.tucitaconnect.R;
import com.techventures.tucitaconnect.activities.splash.SplashActivity;
import com.techventures.tucitaconnect.activities.venue.adapters.SelectSlotsAdapter;
import com.techventures.tucitaconnect.model.context.blockade.BlockadeCompletion;
import com.techventures.tucitaconnect.model.context.blockade.BlockadeContext;
import com.techventures.tucitaconnect.model.domain.blockade.Blockade;
import com.techventures.tucitaconnect.model.domain.blockade.BlockadeAttributes;
import com.techventures.tucitaconnect.model.domain.slot.Slot;
import com.techventures.tucitaconnect.model.domain.venue.Venue;
import com.techventures.tucitaconnect.model.error.AppError;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SelectTimesToBlockFragment extends DialogFragment{

    private MaterialCalendarView calendarView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private OnJustOneDateSelected listener;
    private List<Slot> slots;
    private Calendar calendar;
    private BlockadeContext context;
    private RelativeLayout concealer;
    private TextView closed;

    public interface OnJustOneDateSelected{

        void onJustOneDateSelected(CalendarDay day);

    }
    @Override
    public void onAttach(Context context) {

        setStyle(DialogFragment.STYLE_NO_TITLE, 0);

        super.onAttach(context);

        listener = (OnJustOneDateSelected) context;

    }

    public void setSlots(List<Slot> slots) {

        boolean justOneSelected = calendarView.getSelectedDates().size() == 1;

        if(slots.isEmpty()){

            if(justOneSelected) {

                closed.setVisibility(View.VISIBLE);

            }

        } else {

            calendar.set(Calendar.HOUR_OF_DAY, slots.get(0).getStartHour());

            calendar.set(Calendar.MINUTE, slots.get(0).getStartMinute());

            calendar.set(Calendar.SECOND, 0);

            if (adapter == null) {

                adapter = new SelectSlotsAdapter(slots, calendar);

                recyclerView.setAdapter(adapter);

            } else {

                ((SelectSlotsAdapter) adapter).setSlots(slots);

            }

            adapter.notifyDataSetChanged();

            if (justOneSelected) {

                recyclerView.setVisibility(View.VISIBLE);

                concealer.setVisibility(View.GONE);

            }

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_select_times, container, false);

        calendarView = (MaterialCalendarView) rootView.findViewById(R.id.calendarView);

        concealer = (RelativeLayout) rootView.findViewById(R.id.concealer);

        closed = (TextView) rootView.findViewById(R.id.closed);

        slots = new ArrayList<>();

        calendar = Calendar.getInstance();

        context = BlockadeContext.context(context);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.slots);

        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);

        calendarView.setMinimumDate(calendar);

        calendar.add(Calendar.MONTH, 2);

        int daysToEnd = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - calendar.get(Calendar.DAY_OF_MONTH);

        calendar.add(Calendar.DATE, daysToEnd);

        calendarView.setMaximumDate(calendar);

        layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);

        concealer.setVisibility(View.GONE);

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {

            @Override
            public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {

                concealer.setVisibility(View.VISIBLE);

                closed.setVisibility(View.GONE);

                if (widget.getSelectedDates().size() == 1) {

                    listener.onJustOneDateSelected(widget.getSelectedDate());

                } else {

                    recyclerView.setVisibility(View.GONE);

                    concealer.setVisibility(View.GONE);

                }

            }

        });

        return rootView;

    }

    public void blockades(Venue venue){

        Blockade blockade = new Blockade();

        List<CalendarDay> dates = getDates();

        Date blockadeDate;

        List<String> blockades = new ArrayList<>();

        if(dates.size() == 1 && adapter != null){

            blockade.putType(BlockadeAttributes.typeSlots);

            blockadeDate = dates.get(0).getDate();

            blockadeDate.setMinutes(0);

            blockadeDate.setHours(0);

            blockade.putDate(blockadeDate);

            List<Slot> slots = ((SelectSlotsAdapter)adapter).getSlots();

            for (Slot slot : slots) {

                if(slot.isSelected()) {

                    blockades.add(slot.getObjectId());

                }

            }

            blockade.putDataArray(blockades);

        }else {

            blockade.putType(BlockadeAttributes.typeDays);

            CalendarDay firstDay = dates.get(0);

            for (CalendarDay calendarDay : dates) {

                String dateString = calendarDay.getDay() + " " + calendarDay.getMonth() + " " + calendarDay.getYear();

                blockades.add(dateString);

                if(firstDay.isAfter(calendarDay)) {

                    firstDay = calendarDay;

                }

            }

            blockadeDate = firstDay.getDate();

            blockadeDate.setMinutes(0);

            blockadeDate.setHours(0);

            blockade.putDate(blockadeDate);

            blockade.addAllUnique(BlockadeAttributes.dataArray, blockades);

        }

        context.saveBlockade(blockade, venue, new BlockadeCompletion.ErrorCompletion() {

            @Override
            public void completion(List<Blockade> blockadeList, AppError error) {

                if(error == null) {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(

                            SelectTimesToBlockFragment.this.getContext());

                    String continueString = getString(R.string.continue_option);

                    String successful = getString(R.string.successful_transaction);

                    String message = getString(R.string.app_name);

                    alertDialogBuilder.setTitle(successful)

                            .setPositiveButton(continueString, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int id) {

                                    SplashActivity.goToStart(getContext());

                                }
                            }).setCancelable(false)

                            .setMessage(message).show();

                }

            }
        });
    }

    public List<CalendarDay> getDates() {

        return calendarView.getSelectedDates();

    }

    public List<Slot> getSlots() {

        return slots;
    }
}

package com.example.techventures.tucitaconnect.activities.venue.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.techventures.tucitaconnect.R;
import com.example.techventures.tucitaconnect.activities.venue.adapters.LeftBarAdapter;
import com.example.techventures.tucitaconnect.activities.venue.adapters.SelectSlotsAdapter;
import com.example.techventures.tucitaconnect.model.domain.slot.Slot;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SelectTimesFragment extends DialogFragment{

    private MaterialCalendarView calendarView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private OnJustOneDateSelected listener;
    private List<Slot> slots;
    private Calendar calendar;
    private RelativeLayout concealer;

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

        this.slots.addAll(slots);

        calendar.set(Calendar.HOUR_OF_DAY, slots.get(0).getStartHour());

        calendar.set(Calendar.MINUTE, slots.get(0).getStartMinute());

        calendar.set(Calendar.SECOND, 0);

        if(adapter == null) {

            adapter = new SelectSlotsAdapter(slots.get(0).getDurationMinutes(), slots.size(), calendar);

            recyclerView.setAdapter(adapter);

        }

        recyclerView.setVisibility(View.VISIBLE);

        adapter.notifyDataSetChanged();

        concealer.setVisibility(View.GONE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_select_times, container, false);

        calendarView = (MaterialCalendarView) rootView.findViewById(R.id.calendarView);

        concealer = (RelativeLayout) rootView.findViewById(R.id.concealer);

        slots = new ArrayList<>();

        calendar = Calendar.getInstance();

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



}

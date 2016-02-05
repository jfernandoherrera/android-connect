package com.example.techventures.tucitaconnect.activities.venue.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.techventures.tucitaconnect.R;
import com.example.techventures.tucitaconnect.activities.venue.adapters.SlotsAppointmentsAdapter;
import com.example.techventures.tucitaconnect.activities.venue.adapters.layout.SlotLayoutManager;
import com.example.techventures.tucitaconnect.model.context.slot.SlotCompletion;
import com.example.techventures.tucitaconnect.model.context.slot.SlotContext;
import com.example.techventures.tucitaconnect.model.domain.slot.Slot;
import com.example.techventures.tucitaconnect.model.domain.venue.Venue;
import com.example.techventures.tucitaconnect.model.error.AppError;
import com.example.techventures.tucitaconnect.utils.common.fragment.AppFragment;

import java.util.List;

public class DayAppointmentsFragment extends AppFragment {

    private SlotContext slotContext;
    private List<Slot> slots;
    private TextView noResults;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RelativeLayout concealer;
    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_venues, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);



        concealer = (RelativeLayout) rootView.findViewById(R.id.concealer);

        noResults = (TextView) rootView.findViewById(R.id.noResults);

        return rootView;

    }


    public void setupSlots(Venue venue){

        slotContext = SlotContext.context(slotContext);

        slotContext.loadDaySlots(venue, 6, new SlotCompletion.SlotErrorCompletion() {

            @Override
            public void completion(List<Slot> slotList, AppError error) {

                slots = slotList;

                if(slotList != null && ! slotList.isEmpty()) {

                    int columns = 0;

                    for(Slot slot : slots){

                        slot.setAmount();

                        int amount = slot.getAmount();

                        if(amount > columns){

                            columns = amount;

                        }

                    }

                    layoutManager = new SlotLayoutManager(getContext(), columns);

                    recyclerView.setLayoutManager(layoutManager);

                    adapter = new SlotsAppointmentsAdapter(slots, typeface, columns);

                    recyclerView.setAdapter(adapter);

                    noResults.setVisibility(View.GONE);

                }

                concealer.setVisibility(View.GONE);

            }
        });

    }


}

package com.example.techventures.tucitaconnect.activities.venue.adapters.layout;


import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class SlotLayoutManager extends GridLayoutManager {

    int cols;

    public SlotLayoutManager(Context context, int spanCount) {

        super(context, spanCount);

        cols = spanCount;
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {


        setMeasuredDimension(1000,1400);
    }




}

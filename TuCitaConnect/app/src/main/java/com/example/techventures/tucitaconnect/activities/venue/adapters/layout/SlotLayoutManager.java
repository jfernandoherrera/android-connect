package com.example.techventures.tucitaconnect.activities.venue.adapters.layout;


import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.techventures.tucitaconnect.utils.common.ViewUtils;

public class SlotLayoutManager extends GridLayoutManager {

    int cols;
    int viewSize = 180;
    double size;
    int numSlots;

    public SlotLayoutManager(Context context, int spanCount, int numSlots) {

        super(context, spanCount);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics metrics = new DisplayMetrics();

        wm.getDefaultDisplay().getMetrics(metrics);

        size = metrics.scaledDensity;

        cols = spanCount;

        this.numSlots = numSlots;

    }

    public void setNumSlots(int numSlots) {

        this.numSlots = numSlots;

    }

    public void setCols(int cols) {

        this.cols = cols;

        setSpanCount(cols);

        setMeasuredDimension(getRealWidth() , getRealHeight());

    }

    public int getRealWidth(){

        int width = (int) (viewSize * size * cols);

        return width;

    }

    public int getRealHeight(){

        int height = (int) (viewSize * size * numSlots);

        return height;
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {

        setMeasuredDimension(getRealWidth(), getRealHeight());

    }

}

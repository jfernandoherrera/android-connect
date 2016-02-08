package com.example.techventures.tucitaconnect.utils.common.scroll;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class AppRecyclerView extends RecyclerView{

    public AppRecyclerView(Context context, AttributeSet attrs) {

        super(context, attrs);

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        return false;

    }
}

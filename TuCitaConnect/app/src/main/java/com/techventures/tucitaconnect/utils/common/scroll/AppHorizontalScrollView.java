package com.techventures.tucitaconnect.utils.common.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;


public class AppHorizontalScrollView extends HorizontalScrollView{

    public AppHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public AppHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }




    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
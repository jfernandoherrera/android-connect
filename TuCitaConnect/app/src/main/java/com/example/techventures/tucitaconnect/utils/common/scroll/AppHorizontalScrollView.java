package com.example.techventures.tucitaconnect.utils.common.scroll;

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
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if(ev.getAction()!=MotionEvent.ACTION_DOWN) {

            return super.dispatchTouchEvent(ev);
        }else return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
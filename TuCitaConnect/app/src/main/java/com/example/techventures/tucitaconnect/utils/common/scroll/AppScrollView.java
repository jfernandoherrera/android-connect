package com.example.techventures.tucitaconnect.utils.common.scroll;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;


public class AppScrollView extends ScrollView {

        public AppScrollView(Context context, AttributeSet attrs, int defStyle) {

            super(context, attrs, defStyle);

        }

        public AppScrollView(Context context, AttributeSet attrs) {

            super(context, attrs);

        }


    @Override
        public boolean onTouchEvent(MotionEvent ev) {
            return false;
        }
    }

package com.techventures.tucitaconnect.utils.common.views;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.media.Image;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.ImageButton;

import com.techventures.tucitaconnect.R;
import com.techventures.tucitaconnect.utils.common.AppFont;
import com.techventures.tucitaconnect.utils.common.ResponsiveInteraction;

public class StateButton extends Button{

    boolean open = false;
    Drawable drawableOpen;
    Drawable drawableClosed;

    ScaleDrawable scaleDrawable;

    public StateButton(Context context, AttributeSet attrs) {

        super(context, attrs);

        drawableClosed = getContext().getResources().getDrawable(ResponsiveInteraction.backgroundClosed);

        drawableOpen = getContext().getResources().getDrawable(ResponsiveInteraction.backgroundOpen);

        setText(getResources().getString(R.string.closed));

        AppFont appFont = new AppFont();

        setTypeface(appFont.getAppFontLight(context));

    }

    public void click() {

        open = !open;

        if (open) {

            setText(getResources().getString(R.string.opened ));

            //setCompoundDrawablesWithIntrinsicBounds(drawableOpen, null, null, null);

        } else {

            setText(getResources().getString(R.string.closed));

            //setCompoundDrawablesWithIntrinsicBounds(drawableClosed, null, null, null);

        }

    }


    public boolean getOpen() {

        return open;

    }
}

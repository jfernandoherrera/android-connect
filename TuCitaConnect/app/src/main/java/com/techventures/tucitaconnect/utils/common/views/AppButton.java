package com.techventures.tucitaconnect.utils.common.views;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

import com.techventures.tucitaconnect.utils.common.AppFont;

public class AppButton extends Button{

    public AppButton(Context context, AttributeSet attrs) {

        super(context, attrs);

        AppFont font = new AppFont();

        setTypeface(font.getAppFontMedium(context), Typeface.BOLD);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            setElevation(8);

        }

    }

}

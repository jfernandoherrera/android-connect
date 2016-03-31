package com.techventures.tucitaconnect.utils.common.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;
import com.techventures.tucitaconnect.R;
import com.techventures.tucitaconnect.utils.common.AppFont;

public class AppEditText extends EditText{

    public AppEditText(Context context, AttributeSet attrs) {

        this(context, attrs, 	android.R.attr.editTextStyle);

    }

    public AppEditText(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AppTextView, defStyle, 0);

        boolean isBold = a.getBoolean(R.styleable.AppTextView_bold, false);

        a.recycle();

        AppFont appFont = new AppFont();

        if(isBold) {

            setTypeface(appFont.getAppFontLight(context), Typeface.BOLD);

        } else {

            setTypeface(appFont.getAppFontLight(context));

        }

    }

}

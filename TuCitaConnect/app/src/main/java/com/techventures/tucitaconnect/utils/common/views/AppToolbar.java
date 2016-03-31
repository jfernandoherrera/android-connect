package com.techventures.tucitaconnect.utils.common.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.techventures.tucitaconnect.R;


public class AppToolbar extends Toolbar {

    private TextView textView;

    public AppToolbar(Context context, AttributeSet attrs) {

        super(context, attrs);

        super.setTitle("");

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.toolbar_view, this);

        textView = (TextView) findViewById(R.id.toolbarTextView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            setElevation(8);

        }

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AppToolbar, 0, 0);

        boolean isBack = a.getBoolean(R.styleable.AppToolbar_navigationBack, false);

        if(isBack) {

            setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow));

        }

        a.recycle();

    }

    @Override
    public void setTitle(int resId) {

        textView.setText(resId);

    }

    @Override
    public void setTitle(CharSequence text) {

        textView.setText(text);

    }

}

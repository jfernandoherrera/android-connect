package com.techventures.tucitaconnect.utils.common.views;

import android.content.Context;
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

        setTitle("");

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.toolbar_view, this);

        textView = (TextView) findViewById(R.id.toolbarTextView);

        setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            setElevation(8);

        }

    }

    @Override
    public void setTitle(int resId) {

        textView.setText(resId);

    }

}

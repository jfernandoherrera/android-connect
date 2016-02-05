package com.example.techventures.tucitaconnect.utils.common.fragment;


import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;

public class AppFragment extends Fragment{

    protected Typeface typeface;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

    }

    @Override
    public void onDetach() {

        super.onDetach();

    }

    public void setTypeface(Typeface typeface) {

        this.typeface = typeface;

    }
}

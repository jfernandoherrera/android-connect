package com.example.techventures.tucitaconnect.utils.common;


import android.content.Context;
import android.graphics.Typeface;

public class AppFont {


    public Typeface getAppFont(Context context){

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");

        return typeface;
    }
}

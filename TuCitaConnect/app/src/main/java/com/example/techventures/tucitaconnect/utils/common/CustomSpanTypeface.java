package com.example.techventures.tucitaconnect.utils.common;

import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.TextAppearanceSpan;

public class CustomSpanTypeface extends TextAppearanceSpan{

    private Typeface typeface;

    public CustomSpanTypeface(String family, int style, int size, ColorStateList color, ColorStateList linkColor, Typeface typeface) {

        super(family, style, size, color, linkColor);

        this.typeface = typeface;

    }

    @Override
    public void updateDrawState(TextPaint ds) {

        super.updateDrawState(ds);

        applyCustomTypeFace(ds);

    }

    @Override
    public void updateMeasureState(TextPaint ds) {

        super.updateMeasureState(ds);

        applyCustomTypeFace(ds);

    }

    private void applyCustomTypeFace(Paint ds) {

        int oldStyle;

        Typeface old = ds.getTypeface();

        if (old == null) {

            oldStyle = 0;

        } else {

            oldStyle = old.getStyle();

        }

        int fake = oldStyle & ~typeface.getStyle();

        if ((fake & Typeface.BOLD) != 0) {

            ds.setFakeBoldText(true);

        }

        if ((fake & Typeface.ITALIC) != 0) {

            ds.setTextSkewX(-0.25f);

        }

        ds.setTypeface(typeface);

    }

}

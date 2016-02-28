package com.techventures.tucitaconnect.utils.common;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.ListView;

import static android.view.View.MeasureSpec.AT_MOST;
import static android.view.View.MeasureSpec.makeMeasureSpec;

public class ViewUtils {

    public int childHeight = 100;
    public int parentHeight = 48;
    private final int twelveHoursClock = 12;
    private final int oneDigitNumber = 9;
    private Context context;

    public ViewUtils(Context context) {

        this.context = context;

        if (context != null) {

            setupHeights();

        }

    }

    private void setupHeights() {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics metrics = new DisplayMetrics();

        wm.getDefaultDisplay().getMetrics(metrics);

        double size = metrics.scaledDensity;

        childHeight = (int) (childHeight * size);

        parentHeight = (int) (parentHeight * size);

    }

    private void setupHeights(int childHeight, int parentHeight) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics metrics = new DisplayMetrics();

        wm.getDefaultDisplay().getMetrics(metrics);

        double size = metrics.scaledDensity;

        this.childHeight = (int) (childHeight * size);

        this.parentHeight = (int) (parentHeight * size);

    }

    public void setListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {

            return;

        }

        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {

            View listItem = listAdapter.getView(i, null, listView);

            if (listItem != null) {

                if (listItem instanceof CheckedTextView) {

                    totalHeight += parentHeight;

                } else {

                    totalHeight += childHeight;

                }

            }

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);

        listView.requestLayout();

    }

    public void setListViewHeightBasedOnChildren(ListView listView, int childHeight, int parentHeight) {

        setupHeights(childHeight, parentHeight);

        setListViewHeightBasedOnChildren(listView);

    }

    public void setListViewHeightBasedOnChildrenCountLines(ListView listView, int height) {

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {

            return;

        }

        int totalHeight = 0;

        int desiredWidth = makeMeasureSpec(listView.getWidth(), AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {

            View listItem = listAdapter.getView(i, null, listView);

            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            if (listItem instanceof CheckedTextView) {

                totalHeight += parentHeight;

            } else {

                totalHeight += height;

            }

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight;

        listView.setLayoutParams(params);

        listView.requestLayout();

    }

    public String hourFormat(int hour, int minute) {

        String amPm = hour <= twelveHoursClock ? "AM" : "PM";

        String minuteString = minute <= oneDigitNumber ? "0" + String.valueOf(minute) : String.valueOf(minute);

        hour = hour <= twelveHoursClock ? hour : hour - twelveHoursClock;

        String hourString = " " + String.valueOf(hour) + ":" + minuteString + amPm + " ";

        return hourString;

    }

}

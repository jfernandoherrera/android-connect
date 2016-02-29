package com.techventures.tucitaconnect.utils.common;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.techventures.tucitaconnect.R;


public class AlertDialogError {

    public void noInternetConnectionAlert(Context context) {

        if(context != null) {

            Toast typeMore = Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT);

            typeMore.setGravity(Gravity.CENTER | Gravity.LEFT, 0, 0);

            typeMore.show();

        }

    }

    public void noIsPossible(Context context) {

        Toast typeMore = Toast.makeText(context, R.string.no_is_possible, Toast.LENGTH_SHORT);

        typeMore.setGravity(Gravity.CENTER | Gravity.LEFT, 0, 0);

        typeMore.show();

    }

}

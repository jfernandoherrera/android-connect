package com.techventures.tucitaconnect.activities.account;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.techventures.tucitaconnect.R;
import com.techventures.tucitaconnect.activities.account.fragments.venues.VenuesFragment;
import com.techventures.tucitaconnect.activities.login.LoginActivity;
import com.techventures.tucitaconnect.model.context.user.UserContext;
import com.techventures.tucitaconnect.model.domain.user.User;
import com.techventures.tucitaconnect.utils.common.AppFont;
import com.techventures.tucitaconnect.utils.common.activity.AppToolbarActivity;
import com.mikhaellopez.circularimageview.CircularImageView;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;

public class AccountActivity extends AppToolbarActivity {

    private UserContext userContext;
    private Toolbar toolbar;
    private VenuesFragment venuesFragment;
    //private AppointmentContext appointmentContext;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        userContext = UserContext.context(userContext);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account);

        setToolbar();

       // appointmentContext = AppointmentContext.context(appointmentContext);

        userContext = UserContext.context(userContext);

        venuesFragment = new VenuesFragment();

        setupUser();

        venuesFragment.setUser(user);

        setVenuesFragment();

        venuesShow();

    }

    private void setupUser(){

        user = userContext.currentUser();

    }


    private void setVenuesFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.container, venuesFragment);

        transaction.commit();

    }

    private void venuesHide() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.hide(venuesFragment);

        transaction.commit();

    }

    private void venuesShow() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.show(venuesFragment);

        transaction.commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_logout, menu);

        MenuItem logoutItem = menu.findItem(R.id.action_logout);

        logoutItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                userContext.logout();

                finish();

                LoginActivity.goToLogin(getApplicationContext());

                return true;

            }

        });

        MenuItem contactUs = menu.findItem(R.id.action_contact_us);

        contactUs.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                String email = "jose@amtechventures.com";

                String typeEmail = "message/rfc822";

                Intent intent = new Intent(Intent.ACTION_SEND);

                intent.setType(typeEmail);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});

                Intent mailer = Intent.createChooser(intent, null);

                startActivity(mailer);

                return true;

            }
        });

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }

    public static void goToAccount(Context context) {

        Intent intent = new Intent(context, AccountActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);

    }

}

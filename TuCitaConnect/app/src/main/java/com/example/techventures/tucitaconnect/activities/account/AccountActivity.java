package com.example.techventures.tucitaconnect.activities.account;


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
import com.example.techventures.tucitaconnect.R;
import com.example.techventures.tucitaconnect.activities.account.fragments.venues.VenuesFragment;
import com.example.techventures.tucitaconnect.activities.login.LoginActivity;
import com.example.techventures.tucitaconnect.model.context.user.UserContext;
import com.example.techventures.tucitaconnect.model.domain.user.User;
import com.example.techventures.tucitaconnect.utils.common.AppFont;
import com.mikhaellopez.circularimageview.CircularImageView;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;

public class AccountActivity extends AppCompatActivity {

    private UserContext userContext;
    private Toolbar toolbar;
    private VenuesFragment venuesFragment;
    Typeface typeface;
    //private AppointmentContext appointmentContext;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        userContext = UserContext.context(userContext);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account);

        setToolbar();

        AppFont appFont = new AppFont();

        typeface = appFont.getAppFont(getApplicationContext());

       // appointmentContext = AppointmentContext.context(appointmentContext);

        userContext = UserContext.context(userContext);

        venuesFragment = new VenuesFragment();

        venuesFragment.setTypeface(typeface);

        setupUser();

        venuesFragment.setUser(user);

        setVenuesFragment();

        venuesShow();

    }

    private void setToolbar(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {

            setSupportActionBar(toolbar);

        }

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

    private TextView getActionBarTextView() {

        TextView titleTextView = null;

        String defaultNameTitleMenu = "mTitleTextView";

        try {

            Field field = toolbar.getClass().getDeclaredField(defaultNameTitleMenu);

            field.setAccessible(true);

            titleTextView = (TextView) field.get(toolbar);

        } catch (NoSuchFieldException e) {

        } catch (IllegalAccessException e) {

        }

        return titleTextView;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_logout, menu);

        TextView textView = getActionBarTextView();

        textView.setText(user.getName());

        textView.setTypeface(typeface, Typeface.BOLD);

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

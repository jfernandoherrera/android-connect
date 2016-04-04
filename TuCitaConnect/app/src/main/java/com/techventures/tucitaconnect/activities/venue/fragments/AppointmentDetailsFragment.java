package com.techventures.tucitaconnect.activities.venue.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.techventures.tucitaconnect.R;
import com.techventures.tucitaconnect.activities.venue.adapters.ExpandableWithoutParentAdapter;
import com.techventures.tucitaconnect.model.domain.appointment.Appointment;
import com.techventures.tucitaconnect.model.domain.service.Service;
import com.techventures.tucitaconnect.model.domain.user.User;
import com.techventures.tucitaconnect.model.domain.venue.Venue;
import com.techventures.tucitaconnect.utils.common.ViewUtils;
import com.techventures.tucitaconnect.utils.common.views.AppointmentView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AppointmentDetailsFragment extends DialogFragment {

    private LayoutInflater inflater;
    private ExpandableWithoutParentAdapter adapter;
    private ExpandableListView listViewServices;
    private TextView textTotal;
    private AppointmentView appointmentView;
    private Appointment appointment;
    private List<Service> services;
  //  private OnChangeDate listener;



    public interface OnChangeDate{

        void onChangeDate(Date date);

    }

    @Override
    public void onAttach(Context context) {

        setStyle(DialogFragment.STYLE_NO_TITLE, 0);

        super.onAttach(context);
      //  listener = (OnChangeDate) context;
    }

    @Override
    public void onDetach() {

        super.onDetach();

       // listener = null;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_appointment, container, false);

        appointmentView = (AppointmentView) rootView.findViewById(R.id.appointment);

        textTotal = (TextView) rootView.findViewById(R.id.textPrice);

        TextView total = (TextView) rootView.findViewById(R.id.total);

        listViewServices = (ExpandableListView) rootView.findViewById(R.id.listViewServices);

        Button button = (Button) rootView.findViewById(R.id.changeDate);

        Calendar calendar = Calendar.getInstance();

        button.setVisibility(View.GONE);

        int oneDay = 24;

        calendar.add(Calendar.HOUR, oneDay);

        Date tomorrow = calendar.getTime();

        try {

            if (tomorrow.before(appointment.getDate())) {

                button.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        if (event.getAction() == MotionEvent.ACTION_DOWN) {

                            v.setBackgroundResource(R.drawable.cling_button_pressed);

                        } else if (event.getAction() != MotionEvent.ACTION_MOVE) {

                            v.setBackgroundResource(R.drawable.cling_button_normal);

                        }

                        return false;

                    }

                });

                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                     //   listener.onChangeDate(new Date());

                    }
                });

            } else {

                button.setVisibility(View.GONE);

            }

        }catch (NullPointerException e){

            e.printStackTrace();

        }

        this.inflater = inflater;

        setup();

        return rootView;

    }

    public void setServices(List<Service> services) {

        this.services = services;

    }

    public Appointment getAppointment() {

        return appointment;

    }

    public List<Service> getServices() {

        return services;

    }

    public int[] getDuration(){

        return appointment.getDuration();

    }

    public void setup(){

        setupAppointment();

        setupServices();

        setupTotal();

        setupUser();

    }

    private void setupUser(){

        User user = appointment.getUser();

        appointmentView.setTextName(user.getTelephone());

    }

    private void setupTotal() {

        int total = 0;

        for (Service service : services) {

            total = total + service.getPrice();

        }

        String money = "$" + total;

        textTotal.setText(money);

    }

    private void setupAppointment(){

        String date = appointment.getDate().toLocaleString();

        String telephone = appointment.getTelephone();

        appointmentView.setTextName(telephone);

        appointmentView.setTextDate(date);

    }

    private void setupServices() {

        ViewUtils viewUtils = new ViewUtils(getContext());

        adapter = new ExpandableWithoutParentAdapter(services, listViewServices, viewUtils);

        adapter.setInflater(inflater);

        listViewServices.setAdapter(adapter);

    }

    public void setAppointment(Appointment appointment) {

        this.appointment = appointment;

    }
}

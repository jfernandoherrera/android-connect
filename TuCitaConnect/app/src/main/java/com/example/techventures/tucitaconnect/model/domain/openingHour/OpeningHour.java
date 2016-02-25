package com.example.techventures.tucitaconnect.model.domain.openingHour;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Opening_Hour")
public class OpeningHour extends ParseObject {

    public int getDay() {

        return getInt(OpeningHourAttributes.day);

    }

    public int getStartHour() {

        return getInt(OpeningHourAttributes.startHour);

    }

    public int getEndHour() {

        return getInt(OpeningHourAttributes.endHour);

    }

    public int getStartMinute() {

        return getInt(OpeningHourAttributes.startMinute);

    }

    public int getEndMinute() {

        return getInt(OpeningHourAttributes.endMinute);

    }

    public static ParseQuery<OpeningHour> getQuery() {

        return ParseQuery.getQuery(OpeningHour.class);

    }

}

package com.techventures.tucitaconnect.model.domain.openingHour;

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

    public void putStartHour(int startHour) {

        put(OpeningHourAttributes.startHour, startHour);

    }

    public void putDay(int day) {

        put(OpeningHourAttributes.day, day);

    }

    public void putEndHour(int endHour) {

        put(OpeningHourAttributes.endHour, endHour);

    }

    public void putStartMinute(int startMinute) {

        put(OpeningHourAttributes.startMinute, startMinute);

    }

    public void putEndMinute(int endMinute) {

        put(OpeningHourAttributes.endMinute, endMinute);

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

package com.techventures.tucitaconnect.model.context.slot;

import com.techventures.tucitaconnect.model.domain.slot.Slot;
import com.techventures.tucitaconnect.model.domain.slot.SlotAttributes;
import com.techventures.tucitaconnect.model.error.AppError;
import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;


public class SlotRemote {

    private ParseQuery<Slot> query;

    private void setQuery() {

        query = Slot.getQuery();

    }

    public void cancelQuery() {

        if (query != null) {

            query.cancel();

        }

    }

    public void loadDaySlots(ParseQuery<Slot> slotParseQuery, int day, final SlotCompletion.SlotErrorCompletion completion) {

        query = slotParseQuery;

        query.whereEqualTo(SlotAttributes.day, day);

        query.orderByAscending(SlotAttributes.startHour + "," + SlotAttributes.startMinute);

        query.setCachePolicy(ParseQuery.CachePolicy.IGNORE_CACHE);

        query.clearCachedResult();

        query.findInBackground(new FindCallback<Slot>() {

            @Override
            public void done(List<Slot> objects, ParseException e) {

                AppError appError = e != null ? new AppError(Slot.class.toString(), 0, null) : null;

                completion.completion(objects, appError);

            }

        });

    }

    public void lockSlot(Slot slot, final SaveCallback callback) {

        ParseACL acl = slot.getACL();

        ParseUser user = ParseUser.getCurrentUser();

        acl.setPublicReadAccess(false);

        acl.setPublicWriteAccess(false);

        acl.setReadAccess(user, true);

        acl.setWriteAccess(user, true);

        slot.setACL(acl);

        slot.saveEventually(callback);

    }

    public boolean isLocked(Slot slot) {

        boolean locked = false;

        try {

            ParseACL acl = slot.fetch().getACL();

            locked = acl.getReadAccess(ParseUser.getCurrentUser()) && !acl.getPublicReadAccess();

        } catch (ParseException e) {

            e.printStackTrace();

        }

        return locked;

    }

}

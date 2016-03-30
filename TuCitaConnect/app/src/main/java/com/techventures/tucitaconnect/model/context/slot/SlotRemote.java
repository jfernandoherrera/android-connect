package com.techventures.tucitaconnect.model.context.slot;

import android.util.Log;

import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.techventures.tucitaconnect.activities.venue.adapters.ExpandableWithoutParentAdapter;
import com.techventures.tucitaconnect.model.domain.slot.Slot;
import com.techventures.tucitaconnect.model.domain.slot.SlotAttributes;
import com.techventures.tucitaconnect.model.domain.venue.Venue;
import com.techventures.tucitaconnect.model.error.AppError;
import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

import bolts.AggregateException;


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

    public void setAmount(Slot slot, int amount, final SlotCompletion.SlotErrorCompletion callback) {

        slot.putAmount(amount);

        slot.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                AppError appError = e != null ? new AppError(Slot.class.toString(), 0, null) : null;

                callback.completion(null, appError);

            }
        });

    }


    public void setAmount(List<Slot> slots, int amount, final SlotCompletion.SlotErrorCompletion callback) {

        for(Slot slot : slots) {

            slot.putAmount(amount);

        }

        ParseObject.saveAllInBackground(slots, new SaveCallback() {
            @Override
            public void done(ParseException e) {

                AppError appError = e != null ? new AppError(Slot.class.toString(), 0, null) : null;

                callback.completion(null, appError);

            }
        });

    }

    public void getDuration(ParseQuery<Slot> slotParseQuery, final SlotCompletion.SlotErrorCompletion completion) {

        query = slotParseQuery;

            query.getFirstInBackground(new GetCallback<Slot>() {

                @Override
                public void done(Slot object, ParseException e) {

                    AppError appError = e != null ? new AppError(Slot.class.toString(), 0, null) : null;

                    if (e != null) {

                        completion.completion(-1, appError);

                        e.printStackTrace();

                    } else {

                        completion.completion(object.getDurationMinutes(), null);

                    }
                }

            });

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

    public void findSlots(ParseQuery<Slot> slotParseQuery, final SlotCompletion.SlotErrorCompletion completion) {

        query = slotParseQuery;

        query.findInBackground(new FindCallback<Slot>() {

            @Override
            public void done(List<Slot> slots, ParseException e) {

                AppError appError = e != null ? new AppError(Slot.class.toString(), 0, null) : null;

                completion.completion(slots, appError);

            }

        });

    }

            public void destroyAllSlots(final ParseQuery<Slot> slotParseQuery, final SlotCompletion.SlotErrorCompletion completion) {

             findSlots(slotParseQuery, new SlotCompletion.SlotErrorCompletion() {

                 @Override
                 public void completion(List<Slot> slots, AppError error) {

                     if (error == null && ! slots.isEmpty()) {

                         ParseObject.deleteAllInBackground(slots, new DeleteCallback() {

                             @Override
                             public void done(ParseException e) {

                                 AppError appError = e != null ? new AppError(Slot.class.toString(), 0, null) : null;

                                 if (e != null) {

                                         e.printStackTrace();


                                 } else {

                                    destroyAllSlots(slotParseQuery, completion);

                                 }

                             }
                         });

                     } else {

                         if(error == null ) {

                             completion.completion(1, null);

                         } else {

                         AppError appError = new AppError(Slot.class.toString(), 0, null);

                         completion.completion(0, appError);

                         }

                     }
                 }

                 @Override
                 public void completion(int duration, AppError error) {

                 }

             });

    }

}

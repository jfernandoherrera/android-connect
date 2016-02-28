package com.techventures.tucitaconnect.model.context.slot;

import com.techventures.tucitaconnect.model.domain.slot.Slot;
import com.techventures.tucitaconnect.model.domain.venue.Venue;
import com.techventures.tucitaconnect.model.domain.venue.VenueAttributes;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.SaveCallback;

public class SlotContext {

    private SlotRemote slotRemote;

    public SlotContext() {

        slotRemote = new SlotRemote();
    }

    public static SlotContext context(SlotContext slotContext) {

        if (slotContext == null) {

            slotContext = new SlotContext();

        }

        return slotContext;

    }

    public void loadDaySlots(Venue venue, int day, SlotCompletion.SlotErrorCompletion completion) {

        try {

            ParseRelation<Slot> object = (ParseRelation) venue.get(VenueAttributes.slots);

            ParseQuery<Slot> query = object.getQuery();

            slotRemote.loadDaySlots(query, day, completion);

        }catch (NullPointerException e){

            e.printStackTrace();

        }

    }

    public void lockSlot(Slot slot, SaveCallback callback) {

        slotRemote.lockSlot(slot, callback);

    }

    public boolean isLocked(Slot slot) {

        return slotRemote.isLocked(slot);

    }

}

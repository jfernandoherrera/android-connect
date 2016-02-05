package com.example.techventures.tucitaconnect.model.context.slot;

import com.example.techventures.tucitaconnect.model.domain.slot.Slot;
import com.example.techventures.tucitaconnect.model.error.AppError;
import java.util.List;

public class SlotCompletion {

    public interface SlotErrorCompletion {

        void completion(List<Slot> slotList, AppError error);

    }
}

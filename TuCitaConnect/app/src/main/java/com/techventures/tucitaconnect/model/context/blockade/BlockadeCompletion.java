package com.techventures.tucitaconnect.model.context.blockade;


import com.techventures.tucitaconnect.model.domain.blockade.Blockade;
import com.techventures.tucitaconnect.model.error.AppError;

import java.util.List;

public class BlockadeCompletion {

    public interface ErrorCompletion {

        void completion(List<Blockade> blockadeList, AppError error);

    }

}

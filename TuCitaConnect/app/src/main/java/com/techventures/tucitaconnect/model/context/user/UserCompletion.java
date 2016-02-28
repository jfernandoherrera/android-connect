package com.techventures.tucitaconnect.model.context.user;


import com.techventures.tucitaconnect.model.domain.user.User;
import com.techventures.tucitaconnect.model.error.AppError;

public class UserCompletion {

    public interface UserErrorCompletion {

        void completion(User user, AppError error);

    }

}

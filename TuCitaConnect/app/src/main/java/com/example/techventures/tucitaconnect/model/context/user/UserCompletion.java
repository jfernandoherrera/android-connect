package com.example.techventures.tucitaconnect.model.context.user;


import com.example.techventures.tucitaconnect.model.domain.user.User;
import com.example.techventures.tucitaconnect.model.error.AppError;

public class UserCompletion {

    public interface UserErrorCompletion {

        void completion(User user, AppError error);

    }

}

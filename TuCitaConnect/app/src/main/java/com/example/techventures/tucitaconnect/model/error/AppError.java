package com.example.techventures.tucitaconnect.model.error;

import java.util.Map;

public class AppError {

    private int errorCode;
    private String domain;
    private Map<?, ?> userInfo;

    public AppError(String domain, int errorCode, Map<?, ?> userInfo) {

        this.errorCode = errorCode;

        this.domain = domain;

        this.userInfo = userInfo;

    }

    public int getErrorCode() {

        return errorCode;

    }

    public String getDomain() {

        return domain;

    }

    public Map<?, ?> getUserInfo() {

        return userInfo;

    }

}

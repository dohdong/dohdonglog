package com.dohdonglog.exception;

import org.springframework.core.annotation.Order;

public class InvalidRequest extends dohdonglogException{

    private static final String MESSAGE = "잘못된 요청입니다.";

    public InvalidRequest() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }

}

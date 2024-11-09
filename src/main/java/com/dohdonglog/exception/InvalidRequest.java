package com.dohdonglog.exception;

import lombok.Getter;
import org.springframework.core.annotation.Order;

@Getter
public class InvalidRequest extends dohdonglogException{

    private static final String MESSAGE = "잘못된 요청입니다.";
    public String fieldName;
    public String message;


    public InvalidRequest() {
        super(MESSAGE);
    }

    public InvalidRequest(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }

}

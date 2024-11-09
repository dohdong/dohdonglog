package com.dohdonglog.exception;

public abstract class dohdonglogException extends RuntimeException{


    public dohdonglogException(String message) {
        super(message);
    }

    public dohdonglogException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();


}

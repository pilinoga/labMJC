package com.epam.esm.module3.service.exception;

public class RequestParameterException extends RuntimeException{
    private static final String ERROR_MESSAGE = "requestParameterException";
    private static final int CODE = 20003;

    public RequestParameterException() {
    }

    public RequestParameterException(String message) {
        super(message);
    }

    public static String getErrorMessage() {
        return ERROR_MESSAGE;
    }

    public static int getCode() {
        return CODE;
    }
}

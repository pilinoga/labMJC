package com.epam.esm.module3.service.exception;

public class RequestParameterException extends RuntimeException{
    private static final String errorMessage = "requestParameterException";
    private static final int code = 20003;

    public RequestParameterException() {
    }

    public RequestParameterException(String message) {
        super(message);
    }

    public static String getErrorMessage() {
        return errorMessage;
    }

    public static int getCode() {
        return code;
    }
}

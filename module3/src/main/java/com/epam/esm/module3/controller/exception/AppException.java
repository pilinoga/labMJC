package com.epam.esm.module3.controller.exception;

public class AppException extends RuntimeException{
    private static final String ERROR_MESSAGE = "appException";
    private static final int CODE = 30009;

    public AppException() {
    }

    public AppException(String message) {
        super(message);
    }

    public static String getErrorMessage() {
        return ERROR_MESSAGE;
    }

    public static int getCode() {
        return CODE;
    }
}

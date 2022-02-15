package com.epam.esm.module3.service.exception;

public class NoSuchOrderException extends RuntimeException{
    private static final String ERROR_MESSAGE = "noSuchOrderException";
    private static final int CODE = 20007;

    public NoSuchOrderException() {
    }

    public NoSuchOrderException(String message) {
        super(message);
    }

    public static String getErrorMessage() {
        return ERROR_MESSAGE;
    }

    public static int getCode() {
        return CODE;
    }
}

package com.epam.esm.module3.service.exception;

public class NoSuchTagException extends RuntimeException{
    private static final String ERROR_MESSAGE = "noSuchTagException";
    private static final int CODE = 20005;

    public NoSuchTagException() {
    }

    public NoSuchTagException(String message) {
        super(message);
    }

    public static String getErrorMessage() {
        return ERROR_MESSAGE;
    }

    public static int getCode() {
        return CODE;
    }
}

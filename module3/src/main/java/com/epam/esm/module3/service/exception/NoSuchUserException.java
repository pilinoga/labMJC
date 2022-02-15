package com.epam.esm.module3.service.exception;

public class NoSuchUserException extends RuntimeException{
    private static final String ERROR_MESSAGE = "noSuchUserException";
    private static final int CODE = 20006;

    public NoSuchUserException() {
    }

    public NoSuchUserException(String message) {
        super(message);
    }

    public static String getErrorMessage() {
        return ERROR_MESSAGE;
    }

    public static int getCode() {
        return CODE;
    }
}

package com.epam.esm.module3.service.exception;

public class UniqueLoginException extends RuntimeException{
    private static final String ERROR_MESSAGE = "uniqueLoginException";
    private static final int CODE = 20010;

    public UniqueLoginException() {
    }

    public UniqueLoginException(String message) {
        super(message);
    }

    public static String getErrorMessage() {
        return ERROR_MESSAGE;
    }

    public static int getCode() {
        return CODE;
    }
}

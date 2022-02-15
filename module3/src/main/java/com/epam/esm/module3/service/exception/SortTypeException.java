package com.epam.esm.module3.service.exception;

public class SortTypeException extends RuntimeException{
    private static final String ERROR_MESSAGE = "sortTypeException";
    private static final int CODE = 20004;

    public SortTypeException() {
    }

    public SortTypeException(String message) {
        super(message);
    }

    public static String getErrorMessage() {
        return ERROR_MESSAGE;
    }

    public static int getCode() {
        return CODE;
    }
}

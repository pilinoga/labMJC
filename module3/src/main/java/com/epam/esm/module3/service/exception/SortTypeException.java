package com.epam.esm.module3.service.exception;

public class SortTypeException extends RuntimeException{
    private static final String errorMessage = "sortTypeException";
    private static final int code = 20004;

    public SortTypeException() {
    }

    public SortTypeException(String message) {
        super(message);
    }

    public static String getErrorMessage() {
        return errorMessage;
    }

    public static int getCode() {
        return code;
    }
}

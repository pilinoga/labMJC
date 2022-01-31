package com.epam.esm.exception;

public class SortException extends RuntimeException{
    private static final String errorMessage = "sortException";
    private static final int code = 20001;

    public SortException() {
    }

    public SortException(String message) {
        super(message);
    }

    public static String getErrorMessage() {
        return errorMessage;
    }

    public static int getCode() {
        return code;
    }
}

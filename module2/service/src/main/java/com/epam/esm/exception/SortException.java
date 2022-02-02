package com.epam.esm.exception;

/**
 * SortException is generated in case received invalid parameter.
 */
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

package com.epam.esm.module3.service.exception;

public class NoSuchOrderException extends RuntimeException{
    private static final String errorMessage = "noSuchOrderException";
    private static final int code = 20007;

    public NoSuchOrderException() {
    }

    public NoSuchOrderException(String message) {
        super(message);
    }

    public static String getErrorMessage() {
        return errorMessage;
    }

    public static int getCode() {
        return code;
    }
}

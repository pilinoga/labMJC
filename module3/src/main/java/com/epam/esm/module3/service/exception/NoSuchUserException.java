package com.epam.esm.module3.service.exception;

public class NoSuchUserException extends RuntimeException{
    private static final String errorMessage = "noSuchUserException";
    private static final int code = 20006;

    public NoSuchUserException() {
    }

    public NoSuchUserException(String message) {
        super(message);
    }

    public static String getErrorMessage() {
        return errorMessage;
    }

    public static int getCode() {
        return code;
    }
}

package com.epam.esm.exception.tag;

public class NoSuchTagException extends RuntimeException{
    private static final String errorMessage = "noSuchTagException";
    private static final int code = 10002;

    public NoSuchTagException() {
    }

    public NoSuchTagException(String message) {
        super(message);
    }

    public static String getErrorMessage() {
        return errorMessage;
    }

    public static int getCode() {
        return code;
    }

}

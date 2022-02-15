package com.epam.esm.module3.service.exception;

public class UniqueNameTagException extends RuntimeException{
    private static final String ERROR_MESSAGE = "uniqueNameTagException";
    private static final int CODE = 20008;

    public UniqueNameTagException() {
    }

    public UniqueNameTagException(String message) {
        super(message);
    }

    public static String getErrorMessage() {
        return ERROR_MESSAGE;
    }

    public static int getCode() {
        return CODE;
    }
}

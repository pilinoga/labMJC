package com.epam.esm.module3.service.exception;

public class UniqueNameTagException extends RuntimeException{
    private static final String errorMessage = "uniqueNameTagException";
    private static final int code = 20008;

    public UniqueNameTagException() {
    }

    public UniqueNameTagException(String message) {
        super(message);
    }

    public static String getErrorMessage() {
        return errorMessage;
    }

    public static int getCode() {
        return code;
    }
}

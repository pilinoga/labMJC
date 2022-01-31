package com.epam.esm.exception;

public class BadRequestException extends Exception{
    private static final String errorMessage = "badRequestException";
    private static final int code = 30001;

    public BadRequestException() {
    }

    public static String getErrorMessage() {
        return errorMessage;
    }

    public static int getCode() {
        return code;
    }
}

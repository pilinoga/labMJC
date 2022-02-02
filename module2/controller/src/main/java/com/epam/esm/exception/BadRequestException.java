package com.epam.esm.exception;

/**
 * BadRequestException is generated in case received invalid request.
 */
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

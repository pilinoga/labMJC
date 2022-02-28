package com.epam.esm.module3.controller.exception;

public class UserValidationException extends RuntimeException{
    private static final String ERROR_MESSAGE = "userValidationException";
    private static final int CODE = 30007;

    public UserValidationException() {
    }

    public UserValidationException(String message) {
        super(message);
    }

    public static String getErrorMessage() {
        return ERROR_MESSAGE;
    }

    public static int getCode() {
        return CODE;
    }
}

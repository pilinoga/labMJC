package com.epam.esm.module3.controller.exception;

public class TagValidationException extends RuntimeException{
    private static final String ERROR_MESSAGE = "tagValidationException";
    private static final int CODE = 30001;

    public TagValidationException() {
    }

    public TagValidationException(String message) {
        super(message);
    }
    public static String getErrorMessage() {
        return ERROR_MESSAGE;
    }

    public static int getCode() {
        return CODE;
    }
}

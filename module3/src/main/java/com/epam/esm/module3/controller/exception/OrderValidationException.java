package com.epam.esm.module3.controller.exception;

public class OrderValidationException extends RuntimeException{
    private static final String ERROR_MESSAGE = "orderValidationException";
    private static final int CODE = 30003;

    public OrderValidationException() {
    }

    public OrderValidationException(String message) {
        super(message);
    }

    public static String getErrorMessage() {
        return ERROR_MESSAGE;
    }

    public static int getCode() {
        return CODE;
    }
}

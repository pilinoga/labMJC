package com.epam.esm.module3.controller.exception;

public class OrderValidationException extends RuntimeException{
    private static final String errorMessage = "orderValidationException";
    private static final int code = 30003;

    public OrderValidationException() {
    }

    public OrderValidationException(String message) {
        super(message);
    }

    public static String getErrorMessage() {
        return errorMessage;
    }

    public static int getCode() {
        return code;
    }
}

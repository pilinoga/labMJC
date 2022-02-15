package com.epam.esm.module3.controller.exception;

public class PaginationException extends RuntimeException{
    private static final String ERROR_MESSAGE = "paginationException";
    private static final int CODE = 30003;

    public PaginationException() {
    }

    public PaginationException(String message) {
        super(message);
    }

    public static String getErrorMessage() {
        return ERROR_MESSAGE;
    }

    public static int getCode() {
        return CODE;
    }
}

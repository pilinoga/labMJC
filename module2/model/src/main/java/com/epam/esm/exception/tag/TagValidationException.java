package com.epam.esm.exception.tag;

public class TagValidationException extends RuntimeException{
    private static final String errorMessage = "tagValidationException";
    private static final int code = 10004;

    public TagValidationException() {
    }

    public TagValidationException(String message) {
        super(message);
    }
    public static String getErrorMessage() {
        return errorMessage;
    }

    public static int getCode() {
        return code;
    }
}

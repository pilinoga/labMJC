package com.epam.esm.exception.tag;

public class TagAlreadyExistException extends RuntimeException{
    private static final String errorMessage = "tagAlreadyExistException";
    private static final int code = 10003;

    public TagAlreadyExistException() {
    }

    public TagAlreadyExistException(String message) {
        super(message);
    }

    public static String getErrorMessage() {
        return errorMessage;
    }

    public static int getCode() {
        return code;
    }
}

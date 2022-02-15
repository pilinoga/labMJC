package com.epam.esm.module3.controller.exception;

public class CertificateValidationException extends RuntimeException{
    private static final String ERROR_MESSAGE = "certificateValidationException";
    private static final int CODE = 30002;

    public CertificateValidationException() {
    }

    public CertificateValidationException(String message) {
        super(message);
    }

    public static String getErrorMessage() {
        return ERROR_MESSAGE;
    }

    public static int getCode() {
        return CODE;
    }
}

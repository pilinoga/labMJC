package com.epam.esm.module3.controller.exception;

public class CertificateValidationException extends RuntimeException{
    private static final String errorMessage = "certificateValidationException";
    private static final int code = 30002;

    public CertificateValidationException() {
    }

    public CertificateValidationException(String message) {
        super(message);
    }

    public static String getErrorMessage() {
        return errorMessage;
    }

    public static int getCode() {
        return code;
    }
}

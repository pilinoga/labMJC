package com.epam.esm.exception.certificate;

public class CertificateValidationException extends RuntimeException{
    private static final String errorMessage = "certificateValidationException";
    private static final int code = 10005;

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

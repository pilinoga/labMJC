package com.epam.esm.module3.service.exception;

public class NoSuchCertificateException extends RuntimeException{
    private static final String errorMessage = "noSuchCertificateException";
    private static final int code = 20002;

    public NoSuchCertificateException() {
    }

    public NoSuchCertificateException(String message) {
        super(message);
    }

    public static String getErrorMessage() {
        return errorMessage;
    }

    public static int getCode() {
        return code;
    }
}

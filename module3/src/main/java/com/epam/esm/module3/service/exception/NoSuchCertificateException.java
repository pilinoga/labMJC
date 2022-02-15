package com.epam.esm.module3.service.exception;

public class NoSuchCertificateException extends RuntimeException{
    private static final String ERROR_MESSAGE = "noSuchCertificateException";
    private static final int CODE = 20002;

    public NoSuchCertificateException() {
    }

    public NoSuchCertificateException(String message) {
        super(message);
    }

    public static String getErrorMessage() {
        return ERROR_MESSAGE;
    }

    public static int getCode() {
        return CODE;
    }
}

package com.epam.esm.module3.service.exception;

public class UniqueNameCertificateException extends RuntimeException{
    private static final String ERROR_MESSAGE = "uniqueNameCertificateException";
    private static final int CODE = 20001;

    public UniqueNameCertificateException() {
    }

    public UniqueNameCertificateException(String message) {
        super(message);
    }

    public static String getErrorMessage() {
        return ERROR_MESSAGE;
    }

    public static int getCode() {
        return CODE;
    }
}

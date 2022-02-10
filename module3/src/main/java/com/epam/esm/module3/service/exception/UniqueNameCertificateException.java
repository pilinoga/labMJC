package com.epam.esm.module3.service.exception;

public class UniqueNameCertificateException extends RuntimeException{
    private static final String errorMessage = "uniqueNameCertificateException";
    private static final int code = 20001;

    public UniqueNameCertificateException() {
    }

    public UniqueNameCertificateException(String message) {
        super(message);
    }

    public static String getErrorMessage() {
        return errorMessage;
    }

    public static int getCode() {
        return code;
    }
}

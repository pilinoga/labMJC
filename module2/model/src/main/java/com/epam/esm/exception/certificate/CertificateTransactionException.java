package com.epam.esm.exception.certificate;

public class CertificateTransactionException extends RuntimeException{
    private static final String errorMessage = "certificateTransactionException";
    private static final int code = 10002;

    public CertificateTransactionException() {
    }

    public CertificateTransactionException(String message) {
        super(message);
    }

    public static String getErrorMessage() {
        return errorMessage;
    }

    public static int getCode() {
        return code;
    }
}

package com.epam.esm.exception.certificate;

/**
 * NoSuchCertificateException is generated in case of certificate absence in database.
 */

public class NoSuchCertificateException extends RuntimeException{
    private static final String errorMessage = "noSuchCertificateException";
    private static final int code = 10001;

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

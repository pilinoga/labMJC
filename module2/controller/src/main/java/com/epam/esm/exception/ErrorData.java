package com.epam.esm.exception;

/**
 * Class is designed for representing objets that will be returned in case of exception is generated.
 */
public class ErrorData {
    private String errorMessage;
    private int errorCode;

    public ErrorData() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}

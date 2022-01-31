package com.epam.esm.exception;

import com.epam.esm.config.localization.Translator;
import com.epam.esm.exception.certificate.CertificateTransactionException;
import com.epam.esm.exception.certificate.NoSuchCertificateException;
import com.epam.esm.exception.tag.NoSuchTagException;
import com.epam.esm.exception.tag.TagAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {
    private final Translator translator;

    @Autowired
    public AppExceptionHandler(Translator translator) {
        this.translator = translator;
    }

    @ExceptionHandler
    public ResponseEntity<ErrorData> handleException(NoSuchTagException exception){
        ErrorData data = new ErrorData();
        String message = translator.toLocale(NoSuchTagException.getErrorMessage());
        data.setErrorMessage(message);
        data.setErrorCode(NoSuchTagException.getCode());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorData> handleException(TagAlreadyExistException exception){
        ErrorData data = new ErrorData();
        String message = translator.toLocale(TagAlreadyExistException.getErrorMessage());
        data.setErrorMessage(message);
        data.setErrorCode(TagAlreadyExistException.getCode());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorData> handleException(NoSuchCertificateException exception){
        ErrorData data = new ErrorData();
        String message = translator.toLocale(NoSuchCertificateException.getErrorMessage());
        data.setErrorMessage(message);
        data.setErrorCode(NoSuchCertificateException.getCode());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorData> handleException(SortException exception){
        ErrorData data = new ErrorData();
        String message = translator.toLocale(SortException.getErrorMessage());
        data.setErrorMessage(message);
        data.setErrorCode(SortException.getCode());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorData> handleException(CertificateTransactionException exception){
        ErrorData data = new ErrorData();
        String message = translator.toLocale(CertificateTransactionException.getErrorMessage());
        data.setErrorMessage(message);
        data.setErrorCode(SortException.getCode());
        return new ResponseEntity<>(data, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorData> handleException(Exception exception){
        ErrorData data = new ErrorData();
        String message = translator.toLocale(BadRequestException.getErrorMessage());
        data.setErrorMessage(message);
        data.setErrorCode(BadRequestException.getCode());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}

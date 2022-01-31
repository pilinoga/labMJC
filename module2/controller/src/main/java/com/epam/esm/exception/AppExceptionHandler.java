package com.epam.esm.exception;

import com.epam.esm.config.localization.Translator;
import com.epam.esm.exception.certificate.NoSuchCertificateException;
import com.epam.esm.exception.tag.NoSuchTagException;
import com.epam.esm.exception.tag.TagAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorData> handleException(NoSuchTagException exception){
        ErrorData data = new ErrorData();
        String message = Translator.toLocale(NoSuchTagException.getErrorMessage());
        data.setErrorMessage(message);
        data.setErrorCode(NoSuchTagException.getCode());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorData> handleException(TagAlreadyExistException exception){
        ErrorData data = new ErrorData();
        String message = Translator.toLocale(TagAlreadyExistException.getErrorMessage());
        data.setErrorMessage(message);
        data.setErrorCode(TagAlreadyExistException.getCode());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorData> handleException(NoSuchCertificateException exception){
        ErrorData data = new ErrorData();
        String message = Translator.toLocale(NoSuchCertificateException.getErrorMessage());
        data.setErrorMessage(message);
        data.setErrorCode(NoSuchCertificateException.getCode());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorData> handleException(SortException exception){
        ErrorData data = new ErrorData();
        String message = Translator.toLocale(SortException.getErrorMessage());
        data.setErrorMessage(message);
        data.setErrorCode(SortException.getCode());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorData> handleException(Exception exception){
        ErrorData data = new ErrorData();
        String message = Translator.toLocale(BadRequestException.getErrorMessage());
        data.setErrorMessage(message);
        data.setErrorCode(BadRequestException.getCode());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}

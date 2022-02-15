package com.epam.esm.module3.controller.exception.handler;

import com.epam.esm.module3.controller.exception.CertificateValidationException;
import com.epam.esm.module3.controller.exception.ErrorData;
import com.epam.esm.module3.controller.exception.OrderValidationException;
import com.epam.esm.module3.controller.exception.PaginationException;
import com.epam.esm.module3.controller.exception.TagValidationException;
import com.epam.esm.module3.controller.localization.Translator;
import com.epam.esm.module3.service.exception.NoSuchCertificateException;
import com.epam.esm.module3.service.exception.NoSuchOrderException;
import com.epam.esm.module3.service.exception.NoSuchTagException;
import com.epam.esm.module3.service.exception.NoSuchUserException;
import com.epam.esm.module3.service.exception.RequestParameterException;
import com.epam.esm.module3.service.exception.SortTypeException;
import com.epam.esm.module3.service.exception.UniqueNameTagException;
import com.epam.esm.module3.service.exception.UniqueNameCertificateException;
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
    public ResponseEntity<ErrorData> handleException(NoSuchCertificateException exception){
        ErrorData data = new ErrorData();
        String message = translator.toLocale(NoSuchCertificateException.getErrorMessage());
        data.setErrorMessage(message);
        data.setErrorCode(NoSuchCertificateException.getCode());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorData> handleException(NoSuchOrderException exception){
        ErrorData data = new ErrorData();
        String message = translator.toLocale(NoSuchOrderException.getErrorMessage());
        data.setErrorMessage(message);
        data.setErrorCode(NoSuchOrderException.getCode());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorData> handleException(NoSuchUserException exception){
        ErrorData data = new ErrorData();
        String message = translator.toLocale(NoSuchUserException.getErrorMessage());
        data.setErrorMessage(message);
        data.setErrorCode(NoSuchUserException.getCode());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorData> handleException(SortTypeException exception){
        ErrorData data = new ErrorData();
        String message = translator.toLocale(SortTypeException.getErrorMessage());
        data.setErrorMessage(message);
        data.setErrorCode(SortTypeException.getCode());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorData> handleException(RequestParameterException exception){
        ErrorData data = new ErrorData();
        String message = translator.toLocale(RequestParameterException.getErrorMessage());
        data.setErrorMessage(message);
        data.setErrorCode(RequestParameterException.getCode());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorData> handleException(UniqueNameCertificateException exception){
        ErrorData data = new ErrorData();
        String message = translator.toLocale(UniqueNameCertificateException.getErrorMessage());
        data.setErrorMessage(message);
        data.setErrorCode(UniqueNameCertificateException.getCode());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorData> handleException(TagValidationException exception){
        ErrorData data = new ErrorData();
        String message = translator.toLocale(TagValidationException.getErrorMessage());
        data.setErrorMessage(message);
        data.setErrorCode(TagValidationException.getCode());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorData> handleException(CertificateValidationException exception){
        ErrorData data = new ErrorData();
        String message = translator.toLocale(CertificateValidationException.getErrorMessage());
        data.setErrorMessage(message);
        data.setErrorCode(CertificateValidationException.getCode());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorData> handleException(UniqueNameTagException exception){
        ErrorData data = new ErrorData();
        String message = translator.toLocale(UniqueNameTagException.getErrorMessage());
        data.setErrorMessage(message);
        data.setErrorCode(UniqueNameTagException.getCode());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorData> handleException(OrderValidationException exception){
        ErrorData data = new ErrorData();
        String message = translator.toLocale(OrderValidationException.getErrorMessage());
        data.setErrorMessage(message);
        data.setErrorCode(OrderValidationException.getCode());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorData> handleException(IllegalArgumentException exception){
        ErrorData data = new ErrorData();
        String message = translator.toLocale(PaginationException.getErrorMessage());
        data.setErrorMessage(message);
        data.setErrorCode(PaginationException.getCode());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}

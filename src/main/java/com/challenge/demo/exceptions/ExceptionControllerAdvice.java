package com.challenge.demo.exceptions;

import com.challenge.demo.persistence.exceptions.PersistenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<CustomError> handleEmailException(EmailException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomError(ex.getMessage()));
    }

    @ExceptionHandler(PasswordException.class)
    public ResponseEntity<CustomError> handlePasswordException(PasswordException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomError(ex.getMessage()));
    }

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<CustomError> handlePasswordException(PersistenceException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomError(ex.getMessage()));
    }

    @ExceptionHandler(ExistUserException.class)
    public ResponseEntity<CustomError> handleExistUserException(ExistUserException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomError(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomError> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomError(ex.getMessage()));
    }
}
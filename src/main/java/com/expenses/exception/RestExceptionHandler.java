package com.expenses.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<RestExceptionResponse> handleException(Exception exception) {
        final RestExceptionResponse response = new RestExceptionResponse(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<RestExceptionResponse> handleAuthenticationException(AuthenticationException exception) {
        final RestExceptionResponse apiExceptionResponseDto = new RestExceptionResponse(exception.getMessage());
        return new ResponseEntity<>(apiExceptionResponseDto, HttpStatus.UNAUTHORIZED);
    }

}

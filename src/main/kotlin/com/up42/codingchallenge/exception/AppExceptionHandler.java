package com.up42.codingchallenge.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler
    public ResponseEntity handleAppException(ResponseStatusException responseStatusException){
        return new ResponseEntity(responseStatusException.getReason(),
                responseStatusException.getStatus());
    }
}

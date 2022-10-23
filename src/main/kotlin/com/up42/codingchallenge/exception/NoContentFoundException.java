package com.up42.codingchallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class NoContentFoundException extends ResponseStatusException {
    public NoContentFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }
}

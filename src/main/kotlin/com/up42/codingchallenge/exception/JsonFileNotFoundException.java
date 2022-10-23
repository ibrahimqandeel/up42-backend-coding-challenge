package com.up42.codingchallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class JsonFileNotFoundException extends ResponseStatusException {
    public JsonFileNotFoundException(String reason) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason);
    }
}

package com.lucascostabr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExportNotFoundException extends RuntimeException {
    public ExportNotFoundException(String message) {
        super(message);
    }

    public ExportNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

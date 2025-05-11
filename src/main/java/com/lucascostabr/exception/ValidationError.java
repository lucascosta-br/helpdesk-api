package com.lucascostabr.exception;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends ErrorResponse{
    private List<FieldError> errors = new ArrayList<>();

    public ValidationError(Instant timestamp, Integer status, String message, String path) {
        super(timestamp, status, message, path);
    }

    public void addError(String fieldName, String message) {
        errors.add(new FieldError(fieldName, message));
    }

    public List<FieldError> getErrors() {
        return errors;
    }

    public record FieldError(String fieldName, String message) {}

}

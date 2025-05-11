package com.lucascostabr.exception;

import java.io.IOException;

public class FileNotSavedException extends RuntimeException {
    public FileNotSavedException(String message, IOException e) {
        super(message);
    }
}

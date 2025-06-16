package com.lucascostabr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    public BadRequestException(){
        super("Extensão do Arquivo não suportada");
    }

    public BadRequestException(String message) {
        super(message);
    }

}

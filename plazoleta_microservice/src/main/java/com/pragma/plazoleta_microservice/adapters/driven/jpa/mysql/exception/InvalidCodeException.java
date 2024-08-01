package com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.exception;

public class InvalidCodeException extends RuntimeException{
    public InvalidCodeException(String message) {
        super(message);
    }
}
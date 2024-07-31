package com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.exception;

public class InvalidStatusException extends RuntimeException{
    public InvalidStatusException(String message) {
        super(message);
    }
}

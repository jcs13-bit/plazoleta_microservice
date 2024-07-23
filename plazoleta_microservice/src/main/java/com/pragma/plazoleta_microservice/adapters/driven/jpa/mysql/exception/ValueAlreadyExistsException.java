package com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.exception;

public class ValueAlreadyExistsException extends RuntimeException{
    public ValueAlreadyExistsException(String message) {
            super(message);
    }
}


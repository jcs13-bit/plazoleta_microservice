package com.pragma.plazoleta_microservice.adapters.util.services.exception;

public class DataNotFoundException extends RuntimeException{
    public DataNotFoundException(String message) {
        super(message);
    }
}
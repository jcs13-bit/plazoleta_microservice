package com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(String message) {
        super(message);
    }
}
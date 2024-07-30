package com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.exception;

public class OrderStateChangeException extends RuntimeException{
    public OrderStateChangeException(String message) {
        super(message);
    }
}
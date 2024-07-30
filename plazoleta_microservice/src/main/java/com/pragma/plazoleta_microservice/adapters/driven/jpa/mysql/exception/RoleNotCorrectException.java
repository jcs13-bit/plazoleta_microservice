package com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.exception;

public class RoleNotCorrectException extends RuntimeException{
    public RoleNotCorrectException(String message) {
        super(message);
    }
}

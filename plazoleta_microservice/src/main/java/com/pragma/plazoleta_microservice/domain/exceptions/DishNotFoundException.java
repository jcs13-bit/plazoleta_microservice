package com.pragma.plazoleta_microservice.domain.exceptions;

public class DishNotFoundException extends RuntimeException{

    public DishNotFoundException(String message) {
        super(message);
    }
}

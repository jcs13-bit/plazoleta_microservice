package com.pragma.plazoleta_microservice.domain.exceptions;

public class RestaurantNotFoundException extends RuntimeException{

    public RestaurantNotFoundException(String message) {
        super(message);
    }
}

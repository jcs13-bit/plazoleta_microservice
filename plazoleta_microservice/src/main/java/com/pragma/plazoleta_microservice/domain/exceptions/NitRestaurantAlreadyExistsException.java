package com.pragma.plazoleta_microservice.domain.exceptions;

public class NitRestaurantAlreadyExistsException extends RuntimeException{

    public NitRestaurantAlreadyExistsException(String message) {
        super(message);
    }
}
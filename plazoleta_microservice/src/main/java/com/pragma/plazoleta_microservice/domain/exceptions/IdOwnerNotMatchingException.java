package com.pragma.plazoleta_microservice.domain.exceptions;

public class IdOwnerNotMatchingException extends RuntimeException{

    public IdOwnerNotMatchingException(String message) {
        super(message);
    }
}

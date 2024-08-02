package com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.exception;

public class ErrorSendMessageException extends RuntimeException{
    public ErrorSendMessageException(String message) {
        super(message);
    }
}

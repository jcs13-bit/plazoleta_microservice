package com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.exception;

public class ConstantsAdapter {
    private ConstantsAdapter() {
        throw new IllegalStateException("Utility class");
    }
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_READY = "READY";
    public static final String STATUS_PREPARATION = "PREPARATION";
    public static final String ROLE_BAD = "The user not have the correct role";

    public static final String ORDER_STATE_CHANGE_EXCEPTION = "The order state cannot be changed";

    public static final String DISH_NOT_FOUND_EXCEPTION_MESSAGE = "Dish not found";

    public static final String STATUS_INVALID_EXCEPTION_MESSAGE = "The status is invalid";
    public static final String ORDER_NOT_FOUND = "Order not found";





}

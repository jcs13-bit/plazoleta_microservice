package com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.exception;

public class ConstantsAdapter {
    private ConstantsAdapter() {
        throw new IllegalStateException("Utility class");
    }
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_READY = "READY";
    public static final String STATUS_CANCELLED = "CANCELLED";
    public static final String STATUS_PREPARATION = "PREPARATION";
    public static final String SEND = "SEND";
    public static final String ROLE_BAD = "The user not have the correct role";
    public static final String ERROR_SEND_MESSAGE = "Error send message";

    public static final String MESSAGE_CANCELLED = "Lo sentimos, tu pedido ya está en preparación y no puede cancelarse";

    public static final String ORDER_STATE_CHANGE_EXCEPTION = "The order state cannot be changed";

    public static final String DISH_NOT_FOUND_EXCEPTION_MESSAGE = "Dish not found";

    public static final String STATUS_INVALID_EXCEPTION_MESSAGE = "The status is invalid";
    public static final String ORDER_NOT_FOUND = "Order not found";

    public static final String ERROR_CODE_INVALID_EXCEPTION_MESSAGE = "Error validation code";

    public static final String CODE_INVALID_EXCEPTION_MESSAGE = "The code is invalid";

    public static final String ORDER_CANCELLED = "The order has been successfully canceled";





}

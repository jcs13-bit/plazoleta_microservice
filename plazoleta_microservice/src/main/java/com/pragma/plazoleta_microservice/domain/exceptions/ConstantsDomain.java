package com.pragma.plazoleta_microservice.domain.exceptions;

public class ConstantsDomain {
    private ConstantsDomain() {
        throw new IllegalStateException("Utility class");
    }

    public static final String NIT_ALREADY_EXISTS = "The Restaurant already exists";

    public static final String OWNER_NOT_FOUND = "Owner not found";

    public static final String OWNER ="OWNER";

    public static final String OWNER_NOT_MATCHING ="The id provided is not an owner";

    public static final String DISH_NOT_FOUND_EXCEPTION_MESSAGE = "Dish not found";



}

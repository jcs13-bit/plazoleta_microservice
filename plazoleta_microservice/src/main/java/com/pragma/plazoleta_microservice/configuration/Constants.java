package com.pragma.plazoleta_microservice.configuration;

public class Constants {
    private Constants() {
        throw new IllegalStateException("Utility class");
    }
    public static final String EMPTY_FIELD_EXCEPTION_MESSAGE = "The Field indicated is Empty or Null";
    public static final String FIELD_PHONE_PATTERN_EXCEPTION_MESSAGE = "the cell phone field only allows the + and 13 numbers";
    public static final String FIELD_NIT_NUMBER_PATTERN_EXCEPTION_MESSAGE = "The Field nit has an invalid format";


    public static final String MAX_CHAR_EXCEPTION_MESSAGE = "The field exceeds the maximum character limit specified (13 characters)";
    public static final String VALUE_ALREADY_EXISTS_EXCEPTION_MESSAGE = " indicated is already in use";

    public static final String VALUE_FORMAT = "%s: %s";

    public static final String DISH_NOT_FOUND_EXCEPTION_MESSAGE = "Dish not found";
    public static final String RESTAURANT_NOT_FOUND_EXCEPTION_MESSAGE = "Restaurant not found";

    public static final String USER_NOT_FOUND = "User not found";

    public static final String OWNER_NOT_FOUND = "Owner not found";

    public static final String ROLE_BAD = "The user not have the correct role";

    public static final String ORDER_STATE_CHANGE_EXCEPTION = "The order state cannot be changed";



}

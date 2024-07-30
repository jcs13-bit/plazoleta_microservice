package com.pragma.plazoleta_microservice.adapters.drivin.http.exceptions;

public class DtoConstants {
    private DtoConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String FIELD_NAME_NULL_MESSAGE = "Field 'name' cannot be null";

    public static final String FIELD_NAME_SIZE_MESSAGE = "Field 'Name' cannot be longer than 25 characters";

    public static final String FIELD_CELLPHONE_IS_VALID_MESSAGE = "Field 'Cellphone' Not is a valid Cellphone";

    public static final String FIELD_NIT_NUMBER_IS_VALID_MESSAGE = "Field 'nit' Not is a valid number";


    public static final String FIELD_NAME_IS_VALID_MESSAGE = "Field 'Name' Not is a valid Name";


    public static final String FIELD_PRICE_IS_VALID_MESSAGE = "Field 'Price' Not is a valid Price";

    public static final String FIELD_DESCRIPTION_LENGTH_MESSAGE = "Field 'Description' cannot be longer than 250 characters";

    public static final String FIELD_DESCRIPTION_EMPTY_MESSAGE = "Field 'Description' cannot be empty";

    public static final String FIELD_ID_IS_EMPTY_MESSAGE = "Field 'Id' cannot be empty";


    public static final String FIELD_LAST_NAME_NULL_MESSAGE = "Field 'Last Name' cannot be null";

    public static final String FIELD_LAST_NAME_SIZE_MESSAGE = "Field 'Last Name' cannot be longer than 25 characters";

    public static final String FIELD_DOC_NUMBER_NULL_MESSAGE = "Field 'Doc Number' cannot be null";

    public static final String FIELD_DOC_NUMBER_SIZE_MESSAGE = "Field 'Doc Number' cannot be longer than 25 characters";


    public static final String FIELD_CELLPHONE_NULL_MESSAGE = "Field 'Cellphone' cannot be null";

    public static final String FIELD_CELLPHONE_SIZE_MESSAGE = "Field 'Cellphone' cannot be longer than 13 characters";



    public static final String FIELD_DOC_NUMBER_IS_VALID_MESSAGE = "Field 'Doc Number' Not is a valid Doc Number";



    public static final String FIELD_BIRTH_DATE_NULL_MESSAGE = "Field 'Birth Date' cannot be null";


    public static final String FIELD_EMAIL_NULL_MESSAGE = "Field 'Email' cannot be null";


    public static final String NOT_IS_A_EMAIL = "Not is a valid Email";

    public static final String FIELD_PASSWORD_NULL_MESSAGE = "Field 'Password' cannot be null";


    public static final String FIELD_RESTAURANT_ID_NULL_MESSAGE = "Field 'Restaurant Id' cannot be null";

    public static final String DISHES_IS_EMPTY_MESSAGE = "Field 'Dishes' cannot be empty";


    public static final String FIELD_DISH_ID_NULL_MESSAGE = "Field 'Dish Id' cannot be null";


    public static final String FIELD_QUANTITY_NULL_MESSAGE = "Field 'Quantity' cannot be null";


    public static final String FIELD_QUANTITY_SIZE_MESSAGE = "Field 'Quantity' cannot be less than 1";


    public static final String FIELD_DATE_FUTURE_MESSAGE = "Field 'Date' cannot be in the past";

    public static final String FIELD_CHEF_ID_NULL_MESSAGE = "Field 'Chef Id' cannot be null";


    public static final String FIELD_DISHES_NULL_MESSAGE = "Field 'Dishes' cannot be null";


    public static final String FIELD_CLIENT_ID_NULL_MESSAGE = "Field 'Client Id' cannot be null";
}

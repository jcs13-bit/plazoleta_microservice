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







}

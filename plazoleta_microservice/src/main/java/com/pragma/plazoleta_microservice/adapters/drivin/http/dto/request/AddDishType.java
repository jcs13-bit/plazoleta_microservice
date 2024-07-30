package com.pragma.plazoleta_microservice.adapters.drivin.http.dto.request;


import com.pragma.plazoleta_microservice.adapters.drivin.http.exceptions.DtoConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class AddDishType {
    @NotNull(message = DtoConstants.FIELD_DISH_ID_NULL_MESSAGE)
    private Long dishId;

    @NotNull(message = DtoConstants.FIELD_QUANTITY_NULL_MESSAGE)
    @Min(value = 1, message = DtoConstants.FIELD_QUANTITY_SIZE_MESSAGE)
    private final Integer quantity;
}

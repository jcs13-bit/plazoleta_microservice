package com.pragma.plazoleta_microservice.adapters.drivin.http.dto.request;

import com.pragma.plazoleta_microservice.adapters.drivin.http.exceptions.DtoConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter

public class UpdateDishRequest
{
    @NotNull(message = DtoConstants.FIELD_ID_IS_EMPTY_MESSAGE)
    private Long id;

    @NotBlank(message = DtoConstants.FIELD_DESCRIPTION_EMPTY_MESSAGE)
    @Size(max = 250, message = DtoConstants.FIELD_DESCRIPTION_LENGTH_MESSAGE)
    private String description;
    @NotNull
    @Min(value = 1, message = DtoConstants.FIELD_PRICE_IS_VALID_MESSAGE)
    private Integer price;
}

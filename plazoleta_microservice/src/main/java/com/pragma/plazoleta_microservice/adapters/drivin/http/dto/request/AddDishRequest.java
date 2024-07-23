package com.pragma.plazoleta_microservice.adapters.drivin.http.dto.request;


import com.pragma.plazoleta_microservice.adapters.drivin.http.exceptions.DtoConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class AddDishRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String urlImage;
    @NotNull
    @Min(value = 1, message = DtoConstants.FIELD_PRICE_IS_VALID_MESSAGE)
    private Integer price;
    @NotNull
    private Long categoryId;
    @NotNull
    private Long restaurantId;
}

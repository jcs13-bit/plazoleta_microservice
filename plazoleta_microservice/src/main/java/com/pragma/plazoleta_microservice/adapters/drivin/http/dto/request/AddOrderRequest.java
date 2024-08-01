package com.pragma.plazoleta_microservice.adapters.drivin.http.dto.request;

import com.pragma.plazoleta_microservice.adapters.drivin.http.exceptions.DtoConstants;
import com.pragma.plazoleta_microservice.domain.model.Dish;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
public class AddOrderRequest {


    @NotNull(message = DtoConstants.FIELD_CHEF_ID_NULL_MESSAGE)
    private Long chefId;

    @NotNull(message = DtoConstants.FIELD_DISHES_NULL_MESSAGE)
    @Size(min = 1)
    List<AddDishType> dishesQuantify;

    @NotNull(message = DtoConstants.FIELD_DISHES_NULL_MESSAGE)
    private Long restaurantId;

    @NotNull(message = DtoConstants.FIELD_CLIENT_ID_NULL_MESSAGE)
    private Long clientId;

}

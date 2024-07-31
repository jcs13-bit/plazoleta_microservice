package com.pragma.plazoleta_microservice.adapters.drivin.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
public class OrderResponse {
    private final Long id;

    private final LocalDate date;


    private final Long chefId;

    private List<DishResponse> dishes;


    private final Long clientId;


    private final Long restaurantId;


}

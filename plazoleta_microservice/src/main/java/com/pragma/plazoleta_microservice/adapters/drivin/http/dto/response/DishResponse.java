package com.pragma.plazoleta_microservice.adapters.drivin.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DishResponse {

    private Long id;
    private String name;
    private Integer price;
    private String description;
    private String urlImage;
    private Long restaurantId;
    private Long categoryId;
}

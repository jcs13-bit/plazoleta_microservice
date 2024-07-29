package com.pragma.plazoleta_microservice.adapters.drivin.http.mapper;

import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.response.RestaurantResponse;
import com.pragma.plazoleta_microservice.domain.model.Restaurant;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IRestaurantResponseMapper {

    List<RestaurantResponse> toRestaurantResponseList(List<Restaurant> restaurants);
}

package com.pragma.plazoleta_microservice.adapters.drivin.http.mapper;

import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.request.AddRestaurantRequest;
import com.pragma.plazoleta_microservice.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IRestaurantRequestMapper {
    @Mapping(target = "id", ignore = true)
    Restaurant addRestaurantRequestToRestaurant(AddRestaurantRequest addRestaurantRequest);







}

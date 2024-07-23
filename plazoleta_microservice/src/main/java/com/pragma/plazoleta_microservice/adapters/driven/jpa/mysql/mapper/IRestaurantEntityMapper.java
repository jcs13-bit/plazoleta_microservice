package com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.mapper;


import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.plazoleta_microservice.domain.model.Restaurant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IRestaurantEntityMapper {
    RestaurantEntity toEntity(Restaurant restaurant);

    Restaurant toModel(RestaurantEntity restaurantEntity);

}

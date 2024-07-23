package com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.mapper;

import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.plazoleta_microservice.domain.model.Dish;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IDishEntityMapper {

    Dish toModel(DishEntity dishEntity);

    DishEntity toEntity(Dish dish);
}

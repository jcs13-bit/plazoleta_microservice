package com.pragma.plazoleta_microservice.adapters.drivin.http.mapper;

import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.request.AddDishRequest;
import com.pragma.plazoleta_microservice.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IDishRequestMapper {
    @Mapping(target = "id", ignore = true)
    Dish addRequestToDish(AddDishRequest addDishRequest);


}

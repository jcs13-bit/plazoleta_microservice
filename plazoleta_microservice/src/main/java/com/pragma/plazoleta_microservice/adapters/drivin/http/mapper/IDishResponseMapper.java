package com.pragma.plazoleta_microservice.adapters.drivin.http.mapper;

import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.response.DishResponse;
import com.pragma.plazoleta_microservice.domain.model.Dish;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IDishResponseMapper {
    List<DishResponse> toDishResponseList(List<Dish> dishes);


}

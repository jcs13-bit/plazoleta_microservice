package com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.mapper;


import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.entity.EmployeeWithRestaurantEntity;
import com.pragma.plazoleta_microservice.domain.model.EmployeeWithRestaurant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IEmployeeWithRestaurantEntityMapper {
    EmployeeWithRestaurantEntity toEntity(EmployeeWithRestaurant employeeRestaurant);

    EmployeeWithRestaurant toModel(EmployeeWithRestaurantEntity employeeRestaurantEntity);
}

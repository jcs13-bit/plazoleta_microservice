package com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.entity.EmployeeWithRestaurantEntity;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.mapper.IEmployeeWithRestaurantEntityMapper;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.mapper.IRestaurantEntityMapper;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.repository.IEmployeeWithRestaurantRepository;
import com.pragma.plazoleta_microservice.domain.model.EmployeeWithRestaurant;
import com.pragma.plazoleta_microservice.domain.model.Restaurant;
import com.pragma.plazoleta_microservice.domain.spi.IEmployeeRestaurantPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmployeeRestaurantAdapter implements IEmployeeRestaurantPersistencePort {

    private final IEmployeeWithRestaurantRepository employeeWithRestaurantRepository;
    private  final IEmployeeWithRestaurantEntityMapper employeeWithRestaurantEntityMapper;


    @Override
    public void saveEmployeeWithRestaurant(EmployeeWithRestaurant employeeRestaurant) {

        EmployeeWithRestaurantEntity employeeWithRestaurantEntity = employeeWithRestaurantEntityMapper.toEntity(employeeRestaurant);


        employeeWithRestaurantRepository.save(employeeWithRestaurantEntity);
    }


}

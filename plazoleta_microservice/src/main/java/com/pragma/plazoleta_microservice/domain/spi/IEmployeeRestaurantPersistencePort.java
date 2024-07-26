package com.pragma.plazoleta_microservice.domain.spi;

import com.pragma.plazoleta_microservice.domain.model.EmployeeWithRestaurant;

public interface IEmployeeRestaurantPersistencePort {

    void saveEmployeeWithRestaurant(EmployeeWithRestaurant employeeWithRestaurant);
}

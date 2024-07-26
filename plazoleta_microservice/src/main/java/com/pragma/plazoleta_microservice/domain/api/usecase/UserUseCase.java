package com.pragma.plazoleta_microservice.domain.api.usecase;

import com.pragma.plazoleta_microservice.adapters.client.IUserClient;
import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.response.UserResponse;
import com.pragma.plazoleta_microservice.domain.exceptions.ConstantsDomain;
import com.pragma.plazoleta_microservice.domain.exceptions.OwnerNotFoundException;
import com.pragma.plazoleta_microservice.domain.exceptions.UserNotFoundException;
import com.pragma.plazoleta_microservice.domain.model.EmployeeWithRestaurant;
import com.pragma.plazoleta_microservice.domain.model.Restaurant;
import com.pragma.plazoleta_microservice.domain.spi.IEmployeeRestaurantPersistencePort;
import com.pragma.plazoleta_microservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.plazoleta_microservice.domain.spi.ISecurityPersistencePort;
import org.apache.catalina.User;

import java.sql.SQLOutput;

public class UserUseCase {

    private final IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort;
    private final IUserClient userClient;
    private final ISecurityPersistencePort securityPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;


    public UserUseCase(IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort,
                       IUserClient userClient,
                       ISecurityPersistencePort securityPersistencePort,
                       IRestaurantPersistencePort restaurantPersistencePort) {
        this.employeeRestaurantPersistencePort = employeeRestaurantPersistencePort;
        this.userClient = userClient;
        this.securityPersistencePort = securityPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
    }

    public void  registerEmployee(UserResponse request, Long idRestaurant) {
        validateOwner(idRestaurant);
        Long idEmployee = userClient.registerEmployee(request);
        System.out.println(idEmployee);
        validateEmployee(idEmployee);
        EmployeeWithRestaurant employeeWithRestaurant = new EmployeeWithRestaurant(null, idEmployee, idRestaurant);
        employeeRestaurantPersistencePort.saveEmployeeWithRestaurant(employeeWithRestaurant);
    }

    private void validateEmployee(Long idEmployee) {

        System.out.println(idEmployee);

        UserResponse userResponse = userClient.getUserById(idEmployee);
        if (userResponse == null) {
            throw new UserNotFoundException(ConstantsDomain.USER_NOT_FOUND);
        }
    }

    private void  validateOwner(Long idRestaurant) {

        Restaurant restaurant = restaurantPersistencePort.findById(idRestaurant).get();
        Long idUser = securityPersistencePort.getIdUser();
        System.out.println(idUser);
        System.out.println(restaurantPersistencePort.findById(idRestaurant).get().getOwnerId());

        if (!restaurant.getOwnerId().equals(idUser)) {
            throw new OwnerNotFoundException(ConstantsDomain.OWNER_NOT_FOUND);
        }
    }

    }

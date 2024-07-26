package com.pragma.plazoleta_microservice.domain.api.usecase;

import com.pragma.plazoleta_microservice.adapters.client.IUserClient;
import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.response.OwnerResponse;
import com.pragma.plazoleta_microservice.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta_microservice.domain.exceptions.*;
import com.pragma.plazoleta_microservice.domain.model.Restaurant;
import com.pragma.plazoleta_microservice.domain.spi.IRestaurantPersistencePort;

import java.util.Optional;


public class RestaurantUseCase implements IRestaurantServicePort {

    private IRestaurantPersistencePort restaurantPersistencePort;

    private IUserClient userClient;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IUserClient userClient) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userClient = userClient;
    }


    @Override
    public void saveRestaurant(Restaurant restaurant) {

        validateRestaurant(restaurant.getNit());

        findOwner(restaurant.getOwnerId());

        restaurantPersistencePort.saveRestaurant(restaurant);
    }
    private void findOwner(Long idOwner) {
        OwnerResponse ownerResponse = userClient.getRoleNameByUserId(idOwner);

        if (ownerResponse == null) {
            throw new OwnerNotFoundException(ConstantsDomain.OWNER_NOT_FOUND);
        }
        if (!ownerResponse.roleName().equals(ConstantsDomain.OWNER)) {
            throw new IdOwnerNotMatchingException(ConstantsDomain.OWNER_NOT_MATCHING);

        }
    }


    private void validateRestaurant(String nit) {
        Optional<Restaurant> restaurant = restaurantPersistencePort.findByNit(nit);
        if (restaurant.isPresent()) {
            throw new NitRestaurantAlreadyExistsException(ConstantsDomain.NIT_ALREADY_EXISTS);
        }
    }

    private Restaurant getRestaurantById(Long id) {
        Optional<Restaurant> restaurant = restaurantPersistencePort.findById(id);
        if (!restaurant.isPresent()) {
            throw new RestaurantNotFoundException(ConstantsDomain.OWNER_NOT_FOUND);
        }
        return restaurant.get();
    }

}

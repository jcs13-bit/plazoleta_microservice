package com.pragma.plazoleta_microservice.domain.spi;

import com.pragma.plazoleta_microservice.domain.model.Restaurant;

import java.util.Optional;

public interface IRestaurantPersistencePort {

    void saveRestaurant(Restaurant restaurant);


    Optional<Restaurant> findByNit(String nit);


    Optional<Restaurant> findById(Long id);
}

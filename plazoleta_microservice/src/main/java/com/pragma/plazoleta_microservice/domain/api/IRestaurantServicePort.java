package com.pragma.plazoleta_microservice.domain.api;

import com.pragma.plazoleta_microservice.domain.model.Restaurant;

public interface IRestaurantServicePort {

    void saveRestaurant(Restaurant restaurant);
}

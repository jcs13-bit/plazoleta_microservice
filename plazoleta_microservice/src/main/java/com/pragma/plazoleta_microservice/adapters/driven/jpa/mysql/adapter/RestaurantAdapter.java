package com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.mapper.IRestaurantEntityMapper;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.repository.IRestaurantRepository;
import com.pragma.plazoleta_microservice.domain.model.Restaurant;
import com.pragma.plazoleta_microservice.domain.spi.IRestaurantPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RestaurantAdapter implements IRestaurantPersistencePort {

    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;



    @Override
    public void saveRestaurant(Restaurant restaurant){

        restaurantRepository.save(restaurantEntityMapper.toEntity(restaurant));

    }

    @Override
    public Optional<Restaurant> findByNit(String nit) {
        return restaurantRepository.findByNit(nit).map(restaurantEntityMapper::toModel);
    }


}

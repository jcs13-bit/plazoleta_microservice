package com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.repository;

import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRestaurantRepository  extends JpaRepository<RestaurantEntity, Long> {
    Optional<RestaurantEntity> findByNit(String nit);

    Optional<RestaurantEntity> findByName(String name);

    Optional<RestaurantEntity> findByOwnerId(Long ownerId);

    Optional<RestaurantEntity> findById(Long idRestaurant);

}

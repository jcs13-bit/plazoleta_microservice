package com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.repository;

import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.entity.DishEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDishRepository extends JpaRepository<DishEntity, Long> {
    Optional<DishEntity> findByName(String name);

    Page<DishEntity> findAll(Pageable pageable);
    Page<DishEntity> findAllByRestaurantIdAndCategoryId( Long restaurantId,Long categoryId, Pageable pageable);

    Optional<DishEntity> findByRestaurantIdAndId(Long restaurantId, Long id);

    Page<DishEntity> findAllByRestaurantId(Long restaurantId, Pageable pageable);


}

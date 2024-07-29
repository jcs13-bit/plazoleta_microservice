package com.pragma.plazoleta_microservice.domain.spi;

import com.pragma.plazoleta_microservice.domain.model.Dish;

import java.util.List;
import java.util.Optional;

public interface IDishPersistencePort {
    void saveDish(Dish dish);

    Optional<Dish> findDishById(Long id);

    void updateDish(Dish dish);

    List<Dish> getAllDishes(Integer page, Integer size, Long categoryId, Long restaurantId);


}

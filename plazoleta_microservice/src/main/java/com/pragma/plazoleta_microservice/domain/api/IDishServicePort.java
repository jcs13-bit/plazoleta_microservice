package com.pragma.plazoleta_microservice.domain.api;

import com.pragma.plazoleta_microservice.domain.model.Dish;

import java.util.List;

public interface IDishServicePort {

    void saveDish(Dish dish);

    void updateDish(Long idDish, String description, Integer price);

    void changeStatusDish(Long id);

    List<Dish> getAllDishes(Integer page, Integer size, Long categoryId, Long restaurantId);

}

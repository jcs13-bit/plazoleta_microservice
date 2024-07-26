package com.pragma.plazoleta_microservice.domain.api;

import com.pragma.plazoleta_microservice.domain.model.Dish;

public interface IDishServicePort {

    void saveDish(Dish dish);

    void updateDish(Long idDish, String description, Integer price);

    void changeStatusDish(Long id);

}

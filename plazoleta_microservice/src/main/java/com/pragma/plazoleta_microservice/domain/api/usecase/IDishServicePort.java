package com.pragma.plazoleta_microservice.domain.api.usecase;

import com.pragma.plazoleta_microservice.domain.model.Dish;

public interface IDishServicePort {

    void saveDish(Dish dish);
}

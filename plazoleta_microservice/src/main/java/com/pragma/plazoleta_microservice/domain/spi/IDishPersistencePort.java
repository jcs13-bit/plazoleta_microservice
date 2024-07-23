package com.pragma.plazoleta_microservice.domain.spi;

import com.pragma.plazoleta_microservice.domain.model.Dish;

public interface IDishPersistencePort {
    void saveDish(Dish dish);
}

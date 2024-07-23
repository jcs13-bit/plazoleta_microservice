package com.pragma.plazoleta_microservice.domain.api.usecase;

import com.pragma.plazoleta_microservice.domain.model.Dish;
import com.pragma.plazoleta_microservice.domain.model.Restaurant;
import com.pragma.plazoleta_microservice.domain.spi.IDishPersistencePort;

public class DishUseCase implements IDishServicePort{
    private final IDishPersistencePort dishPersistencePort;
    public DishUseCase(IDishPersistencePort dishPersistencePort) {
        this.dishPersistencePort = dishPersistencePort;
    }
    @Override
    public void saveDish(Dish dish) {
        dish.setActive(true);
        dishPersistencePort.saveDish(dish);
    }


}

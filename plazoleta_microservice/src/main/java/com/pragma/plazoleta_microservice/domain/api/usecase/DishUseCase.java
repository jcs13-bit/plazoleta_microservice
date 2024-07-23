package com.pragma.plazoleta_microservice.domain.api.usecase;

import com.pragma.plazoleta_microservice.domain.exceptions.ConstantsDomain;
import com.pragma.plazoleta_microservice.domain.exceptions.DishNotFoundException;
import com.pragma.plazoleta_microservice.domain.model.Dish;
import com.pragma.plazoleta_microservice.domain.spi.IDishPersistencePort;

import java.util.Optional;

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



    @Override
    public void updateDish(Long idDish, String description, Integer price) {
        Dish dish = getDishById(idDish);
        dish.setDescription(description);
        dish.setPrice(price);
        dishPersistencePort.updateDish(dish);

    }

    private Dish getDishById(Long id) {
        Optional<Dish> dishSearch = dishPersistencePort.findDishById(id);
        if(dishSearch.isEmpty()){
            throw new DishNotFoundException(ConstantsDomain.DISH_NOT_FOUND_EXCEPTION_MESSAGE);
        }
        return dishSearch.get();
    }



}

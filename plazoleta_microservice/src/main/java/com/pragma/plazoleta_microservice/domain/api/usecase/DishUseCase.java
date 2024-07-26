package com.pragma.plazoleta_microservice.domain.api.usecase;

import com.pragma.plazoleta_microservice.domain.api.IDishServicePort;
import com.pragma.plazoleta_microservice.domain.exceptions.ConstantsDomain;
import com.pragma.plazoleta_microservice.domain.exceptions.DishNotFoundException;
import com.pragma.plazoleta_microservice.domain.exceptions.OwnerNotFoundException;
import com.pragma.plazoleta_microservice.domain.model.Dish;
import com.pragma.plazoleta_microservice.domain.model.Restaurant;
import com.pragma.plazoleta_microservice.domain.spi.IDishPersistencePort;
import com.pragma.plazoleta_microservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.plazoleta_microservice.domain.spi.ISecurityPersistencePort;

import java.util.Optional;

public class DishUseCase implements IDishServicePort {
    private final IDishPersistencePort dishPersistencePort;
    private final ISecurityPersistencePort securityPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;


    public DishUseCase(IDishPersistencePort dishPersistencePort, ISecurityPersistencePort securityPersistencePort, IRestaurantPersistencePort restaurantPersistencePort) {
        this.dishPersistencePort = dishPersistencePort;
        this.securityPersistencePort = securityPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
    }
    @Override
    public void saveDish(Dish dish) {
        validateOwner(dish.getRestaurantId());
        dish.setActive(true);
        dishPersistencePort.saveDish(dish);
    }



    @Override
    public void updateDish(Long idDish, String description, Integer price) {


        Dish dish = getDishById(idDish);

        validateOwner(dish.getRestaurantId());
        dish.setDescription(description);
        dish.setPrice(price);
        dishPersistencePort.updateDish(dish);

    }

    private void  validateOwner(Long idRestaurant) {

        Restaurant restaurant = restaurantPersistencePort.findById(idRestaurant).get();
        Long idUser = securityPersistencePort.getIdUser();
        if (!restaurant.getOwnerId().equals(idUser)) {
            throw new OwnerNotFoundException(ConstantsDomain.OWNER_NOT_FOUND);
        }
    }

    private Dish getDishById(Long id) {
        Optional<Dish> dishSearch = dishPersistencePort.findDishById(id);
        if(dishSearch.isEmpty()){
            throw new DishNotFoundException(ConstantsDomain.DISH_NOT_FOUND_EXCEPTION_MESSAGE);
        }
        return dishSearch.get();
    }



}

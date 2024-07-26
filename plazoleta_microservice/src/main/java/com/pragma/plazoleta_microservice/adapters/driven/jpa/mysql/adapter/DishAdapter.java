package com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.mapper.IDishEntityMapper;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.repository.IDishRepository;
import com.pragma.plazoleta_microservice.domain.model.Dish;
import com.pragma.plazoleta_microservice.domain.spi.IDishPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class DishAdapter  implements IDishPersistencePort {

    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;


    @Override
    public void saveDish(Dish dish){
        dish.setActive(true);
        dishRepository.save(dishEntityMapper.toEntity(dish));
    }

    @Override
    public Optional<Dish> findDishById(Long id) {
        return dishRepository.findById(id).map(dishEntityMapper::toModel);
    }

    @Override
    public void updateDish(Dish dish) {
        dishRepository.save(dishEntityMapper.toEntity(dish));
    }



}

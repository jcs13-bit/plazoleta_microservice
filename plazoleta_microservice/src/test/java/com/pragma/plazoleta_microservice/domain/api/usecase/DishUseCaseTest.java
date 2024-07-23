package com.pragma.plazoleta_microservice.domain.api.usecase;

import com.pragma.plazoleta_microservice.domain.exceptions.DishNotFoundException;
import com.pragma.plazoleta_microservice.domain.model.Dish;
import com.pragma.plazoleta_microservice.domain.spi.IDishPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DishUseCaseTest {
    @Mock
    private IDishPersistencePort dishPersistencePort;

    private DishUseCase dishUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        dishUseCase = new DishUseCase(dishPersistencePort);
    }

    @Test
    void testSaveDish() {
        Dish dish = new Dish(4l, "test description", 150, "description", "urlImage",222L, 333L, true);
        dishUseCase.saveDish(dish);
        verify(dishPersistencePort).saveDish(dish);
    }

    @Test
    void testUpdateDish() {
        Long idDish = 1L;
        String description = "new description";
        Integer price = 100;
        Dish dish = new Dish(2L, "test description", 150, "description", "urlImage", 222L, 333L, true);
        when(dishPersistencePort.findDishById(idDish)).thenReturn(Optional.of(dish));
        dishUseCase.updateDish(idDish, description, price);
        verify(dishPersistencePort).updateDish(dish);
        assertEquals(description, dish.getDescription());
        assertEquals(price, dish.getPrice());
    }

    @Test
    void testUpdateDishNotFound() {
        Long idDish = 1L;
        String description = "new description";
        Integer price = 100;
        when(dishPersistencePort.findDishById(idDish)).thenReturn(Optional.empty());
        assertThrows(DishNotFoundException.class, () -> dishUseCase.updateDish(idDish, description, price));
    }

}
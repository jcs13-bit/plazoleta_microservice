package com.pragma.plazoleta_microservice.domain.api.usecase;

import com.pragma.plazoleta_microservice.domain.model.Dish;
import com.pragma.plazoleta_microservice.domain.spi.IDishPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class DishUseCaseTest {
    @Mock
    private IDishPersistencePort dishPersistencePort;

    private DishUseCase dishUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        dishUseCase = new DishUseCase(dishPersistencePort);
    }

    @Test
    public void testSaveDish() {
        Dish dish = new Dish(22L, "test", 100, "test", "test", 1L, 1L, true);
        dishUseCase.saveDish(dish);
        verify(dishPersistencePort).saveDish(dish);
        assertTrue(dish.getActive());
    }


}
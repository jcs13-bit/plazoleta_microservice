package com.pragma.plazoleta_microservice.adapters.drivin.http.controller;

import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.request.AddDishRequest;
import com.pragma.plazoleta_microservice.adapters.drivin.http.mapper.IDishRequestMapper;
import com.pragma.plazoleta_microservice.domain.api.usecase.IDishServicePort;
import com.pragma.plazoleta_microservice.domain.model.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DishRestControllerAdapterTest {
    @Mock
    private IDishServicePort dishServicePort;
    @Mock
    private IDishRequestMapper dishRequestMapper;

    private DishRestControllerAdapter dishRestControllerAdapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        dishRestControllerAdapter = new DishRestControllerAdapter(dishServicePort, dishRequestMapper);
    }

    @Test
    public void testAddDish() {
        AddDishRequest request = new AddDishRequest("test", "description", "test", 100, 1L, 1L);
        Dish dish = new Dish(12L, "test", 100, "description", "test", 1L, 1L, true);
        when(dishRequestMapper.addRequestToDish(request)).thenReturn(dish);

        ResponseEntity<Void> response = dishRestControllerAdapter.addDish(request);

        verify(dishServicePort).saveDish(dish);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

}
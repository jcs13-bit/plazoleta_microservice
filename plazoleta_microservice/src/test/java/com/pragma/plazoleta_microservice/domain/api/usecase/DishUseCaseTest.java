package com.pragma.plazoleta_microservice.domain.api.usecase;


import com.pragma.plazoleta_microservice.domain.api.IDishServicePort;

import com.pragma.plazoleta_microservice.domain.exceptions.ConstantsDomain;
import com.pragma.plazoleta_microservice.domain.exceptions.DishNotFoundException;
import com.pragma.plazoleta_microservice.domain.exceptions.OwnerNotFoundException;
import com.pragma.plazoleta_microservice.domain.exceptions.RestaurantNotFoundException;
import com.pragma.plazoleta_microservice.domain.model.Dish;
import com.pragma.plazoleta_microservice.domain.model.Restaurant;
import com.pragma.plazoleta_microservice.domain.spi.IDishPersistencePort;
import com.pragma.plazoleta_microservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.plazoleta_microservice.domain.spi.ISecurityPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DishUseCaseTest { @Mock
private IDishPersistencePort dishPersistencePort;

    @Mock
    private ISecurityPersistencePort securityPersistencePort;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    private IDishServicePort dishUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        dishUseCase = new DishUseCase(dishPersistencePort, securityPersistencePort, restaurantPersistencePort);
    }


    @Test
    void testSaveDish() {
        Dish dish = new Dish(4L, "test description", 150, "description", "urlImage", 222L, 333L, true);
        Restaurant restaurant = new Restaurant(222L, "Sample Restaurant", "123456789", "123 Main Street", "555-1234", "https://sample.com/logo.png",  333L); // Agrega un objeto Restaurant
        when(restaurantPersistencePort.findById(dish.getRestaurantId())).thenReturn(Optional.of(restaurant));
        when(securityPersistencePort.getIdUser()).thenReturn(333L);

        dishUseCase.saveDish(dish);

        verify(restaurantPersistencePort).findById(dish.getRestaurantId());
        verify(securityPersistencePort).getIdUser();
        verify(dishPersistencePort).saveDish(dish);
        assertTrue(dish.getActive());
    }

    @Test
    void testSaveDishOwnerNotFound() {
        Dish dish = new Dish(4L, "test description", 150, "description", "urlImage", 222L, 333L, true);
        Restaurant restaurant = new Restaurant(222L, "Sample Restaurant", "123456789", "123 Main Street", "555-1234", "https://sample.com/logo.png", 444L); // Cambia el ownerId a un valor diferente
        when(restaurantPersistencePort.findById(dish.getRestaurantId())).thenReturn(Optional.of(restaurant));
        when(securityPersistencePort.getIdUser()).thenReturn(333L);

        assertThrows(OwnerNotFoundException.class, () -> dishUseCase.saveDish(dish));

        verify(restaurantPersistencePort).findById(dish.getRestaurantId());
        verify(securityPersistencePort).getIdUser();
        verify(dishPersistencePort, never()).saveDish(dish);
    }

    @Test
    void testUpdateDish() {
        Long idDish = 1L;
        String description = "new description";
        Integer price = 100;
        Dish dish = new Dish(2L, "test description", 150, "description", "urlImage", 222L, 333L, true);
        Restaurant restaurant = new Restaurant(222L, "Sample Restaurant", "123456789", "123 Main Street", "555-1234", "https://sample.com/logo.png", 333L); // Agrega un objeto Restaurant
        when(dishPersistencePort.findDishById(idDish)).thenReturn(Optional.of(dish));
        when(restaurantPersistencePort.findById(dish.getRestaurantId())).thenReturn(Optional.of(restaurant));
        when(securityPersistencePort.getIdUser()).thenReturn(333L);

        dishUseCase.updateDish(idDish, description, price);

        verify(dishPersistencePort).findDishById(idDish);
        verify(restaurantPersistencePort).findById(dish.getRestaurantId());
        verify(securityPersistencePort).getIdUser();
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

        verify(dishPersistencePort).findDishById(idDish);
        verify(restaurantPersistencePort, never()).findById(anyLong());
        verify(securityPersistencePort, never()).getIdUser();
        verify(dishPersistencePort, never()).updateDish(any(Dish.class));
    }

    @Test
    void testUpdateDishOwnerNotFound() {
        Long idDish = 1L;
        String description = "new description";
        Integer price = 100;
        Dish dish = new Dish(2L, "test description", 150, "description", "urlImage", 222L, 333L, true);
        Restaurant restaurant = new Restaurant(222L, "Sample Restaurant", "123456789", "123 Main Street", "555-1234", "https://sample.com/logo.png",444L);
        when(dishPersistencePort.findDishById(idDish)).thenReturn(Optional.of(dish));
        when(restaurantPersistencePort.findById(dish.getRestaurantId())).thenReturn(Optional.of(restaurant));
        when(securityPersistencePort.getIdUser()).thenReturn(333L);

        assertThrows(OwnerNotFoundException.class, () -> dishUseCase.updateDish(idDish, description, price));

        verify(dishPersistencePort).findDishById(idDish);
        verify(restaurantPersistencePort).findById(dish.getRestaurantId());
        verify(securityPersistencePort).getIdUser();
        verify(dishPersistencePort, never()).updateDish(dish);
    }

    @Test
    void testChangeStatusDish() {
        Long idDish = 1L;
        Dish dish = new Dish(2L, "test description", 150, "description", "urlImage", 222L, 333L, true);
        when(dishPersistencePort.findDishById(idDish)).thenReturn(Optional.of(dish));
        when(securityPersistencePort.getIdUser()).thenReturn(333L);
    }
    @Test
    void testGetAllDishes_RestaurantNotFound() {
        Long restaurantId = 1L;
        when(restaurantPersistencePort.findById(restaurantId)).thenReturn(Optional.empty());

        RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class, () -> {
            dishUseCase.getAllDishes(0, 3, 1L, restaurantId);
        });

        assertEquals(ConstantsDomain.RESTAURANT_NOT_FOUND, exception.getMessage());
        verify(restaurantPersistencePort).findById(restaurantId);
        verify(dishPersistencePort, never()).getAllDishes(anyInt(), anyInt(), anyLong(), anyLong());
    }

    @Test
    void testGetAllDishes_Success() {
        Long restaurantId = 1L;
        Long categoryId = 1L;
        Restaurant restaurant = new Restaurant(restaurantId, "Test Restaurant", "123456789", "123 Main Street", "555-1234", "https://sample.com/logo.png", 333L);

        Dish dish1 = new Dish(1L, "Sushi", 150, "Sushi Description", "urlImage", categoryId, restaurantId, true);
        Dish dish2 = new Dish(2L, "Burger", 100, "Burger Description", "urlImage", categoryId, restaurantId, true);
        Dish dish3 = new Dish(3L, "Pasta", 120, "Pasta Description", "urlImage", categoryId, restaurantId, true);
        List<Dish> dishes = Arrays.asList(dish3, dish1, dish2);

        when(restaurantPersistencePort.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(dishPersistencePort.getAllDishes(0, 3, categoryId, restaurantId)).thenReturn(dishes);

        List<Dish> result = dishUseCase.getAllDishes(0, 3, categoryId, restaurantId);

        assertEquals(3, result.size());
        assertEquals("Burger", result.get(0).getName());
        assertEquals("Pasta", result.get(1).getName());
        assertEquals("Sushi", result.get(2).getName());

        verify(restaurantPersistencePort).findById(restaurantId);
        verify(dishPersistencePort).getAllDishes(0, 3, categoryId, restaurantId);
    }




}
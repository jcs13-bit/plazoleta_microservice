package com.pragma.plazoleta_microservice.domain.api.usecase;

import com.pragma.plazoleta_microservice.adapters.client.IUserClient;
import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.response.OwnerResponse;
import com.pragma.plazoleta_microservice.domain.exceptions.ConstantsDomain;
import com.pragma.plazoleta_microservice.domain.exceptions.IdOwnerNotMatchingException;
import com.pragma.plazoleta_microservice.domain.exceptions.NitRestaurantAlreadyExistsException;
import com.pragma.plazoleta_microservice.domain.exceptions.OwnerNotFoundException;
import com.pragma.plazoleta_microservice.domain.model.Restaurant;
import com.pragma.plazoleta_microservice.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RestaurantUseCaseTest {
    private RestaurantUseCase restaurantUseCase;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IUserClient userClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        restaurantUseCase = new RestaurantUseCase(restaurantPersistencePort, userClient);
    }





    @Test
    public void saveRestaurant_whenRestaurantAlreadyExists_throwsNitRestaurantAlreadyExistsException() {
        // Arrange
        Restaurant restaurant = new Restaurant(1l,"Sample Restaurant", "123456789", "123 Main Street", "555-1234", "https://sample.com/logo.png", 101l);


        // Set up mock behavior
        when(restaurantPersistencePort.findByNit("123456789")).thenReturn(Optional.of(new Restaurant( 1l,"Sample Restaurant", "123456789", "123 Main Street", "555-1234", "https://sample.com/logo.png", 101l)));

        // Act and Assert
        assertThrows(NitRestaurantAlreadyExistsException.class, () -> restaurantUseCase.saveRestaurant(restaurant));
    }
    @Test
    public void saveRestaurant_whenOwnerNotFound_throwsOwnerNotFoundException() {
        // Arrange
        Restaurant restaurant = new Restaurant(1l,"Sample Restaurant", "123456789", "123 Main Street", "555-1234", "https://sample.com/logo.png", 123L);
        when(userClient.getRoleNameByUserId(123L)).thenReturn(null);

        // Act and Assert
        assertThrows(OwnerNotFoundException.class, () -> restaurantUseCase.saveRestaurant(restaurant));
    }

    @Test
    public void saveRestaurant_whenOwnerIsNotOwner_throwsIdOwnerNotMatchingException() {
        // Arrange
        Restaurant restaurant = new Restaurant(1l,"Sample Restaurant", "123456789", "123 Main Street", "555-1234", "https://sample.com/logo.png", 123L);
        restaurant.setOwnerId(123L);
        when(userClient.getRoleNameByUserId(123L)).thenReturn(new OwnerResponse("notOwner"));

        // Act and Assert
        assertThrows(IdOwnerNotMatchingException.class, () -> restaurantUseCase.saveRestaurant(restaurant));
    }

    @Test
    public void saveRestaurant_whenRestaurantDoesNotExistAndOwnerIsFound_savesRestaurant() {
        // Arrange
        Restaurant restaurant = new Restaurant(1l,"Sample Restaurant", "123456789", "123 Main Street", "555-1234", "https://sample.com/logo.png", 123L);
        restaurant.setNit("123456789");
        restaurant.setOwnerId(123L);
        when(restaurantPersistencePort.findByNit("123456789")).thenReturn(Optional.empty());
        when(userClient.getRoleNameByUserId(123L)).thenReturn(new OwnerResponse(ConstantsDomain.OWNER));

        // Act
        restaurantUseCase.saveRestaurant(restaurant);

        // Assert
        verify(restaurantPersistencePort).saveRestaurant(restaurant);
    }



    @Test
    void testGetAllRestaurants() {
        // Arrange
        Restaurant restaurant1 = new Restaurant(2L, "Sushi Place", "123456789", "123 Main Street", "555-1234", "https://sample.com/logo.png", 101L);
        Restaurant restaurant2 = new Restaurant(3L,"Burger Joint","989", "test Main Street", "555-1234", "testUrl", 1032L);
        Restaurant restaurant3 = new Restaurant(4L,"Pasta House","1032", "test calle", "555-1234", "testUrl", 108L);

        when(restaurantPersistencePort.getAllRestaurants(0, 3))
                .thenReturn(Arrays.asList(restaurant3, restaurant1, restaurant2));

        // Act
        List<Restaurant> result = restaurantUseCase.getAllRestaurants(0, 3);

        // Assert
        assertEquals(3, result.size());
        assertEquals("Burger Joint", result.get(0).getName());
        assertEquals("Pasta House", result.get(1).getName());
        assertEquals("Sushi Place", result.get(2).getName());
    }


}
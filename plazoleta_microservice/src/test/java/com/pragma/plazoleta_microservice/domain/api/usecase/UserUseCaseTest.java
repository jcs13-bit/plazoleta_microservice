package com.pragma.plazoleta_microservice.domain.api.usecase;

import com.pragma.plazoleta_microservice.adapters.client.IUserClient;
import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.response.UserResponse;
import com.pragma.plazoleta_microservice.domain.exceptions.OwnerNotFoundException;
import com.pragma.plazoleta_microservice.domain.exceptions.UserNotFoundException;
import com.pragma.plazoleta_microservice.domain.model.EmployeeWithRestaurant;
import com.pragma.plazoleta_microservice.domain.model.Restaurant;
import com.pragma.plazoleta_microservice.domain.spi.IEmployeeRestaurantPersistencePort;
import com.pragma.plazoleta_microservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.plazoleta_microservice.domain.spi.ISecurityPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserUseCaseTest {

    @Mock
    private IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort;

    @Mock
    private IUserClient userClient;

    @Mock
    private ISecurityPersistencePort securityPersistencePort;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @InjectMocks
    private UserUseCase userUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterEmployeeSuccess() {
        // Given
        UserResponse userResponse = new UserResponse(1L, "username", "password", "firstName", "lastName", "email", LocalDate.of(2020, 1, 1), "role");
        Restaurant restaurant = new Restaurant(2L, "Sample Restaurant", "123456789", "123 Main Street", "555-1234", "https://sample.com/logo.png", 1L);
        restaurant.setOwnerId(1L);

        when(restaurantPersistencePort.findById(any(Long.class))).thenReturn(Optional.of(restaurant));
        when(securityPersistencePort.getIdUser()).thenReturn(1L);
        when(userClient.registerEmployee(any(UserResponse.class))).thenReturn(1L);
        when(userClient.getUserById(any(Long.class))).thenReturn(userResponse);

        // When
        userUseCase.registerEmployee(userResponse, 2L);

        // Then
        verify(employeeRestaurantPersistencePort, times(1)).saveEmployeeWithRestaurant(any(EmployeeWithRestaurant.class));
    }

    @Test
    void testRegisterEmployeeUserNotFound() {
        // Given
        UserResponse userResponse = new UserResponse(1L, "username", "password", "firstName", "lastName", "email", LocalDate.of(2020, 1, 1), "role");
        Restaurant restaurant = new Restaurant(2L, "Sample Restaurant", "123456789", "123 Main Street", "555-1234", "https://sample.com/logo.png", 1L);
        restaurant.setOwnerId(1L);

        when(restaurantPersistencePort.findById(any(Long.class))).thenReturn(Optional.of(restaurant));
        when(securityPersistencePort.getIdUser()).thenReturn(1L);
        when(userClient.registerEmployee(any(UserResponse.class))).thenReturn(1L);
        when(userClient.getUserById(any(Long.class))).thenReturn(null);

        // When & Then
        assertThrows(UserNotFoundException.class, () -> userUseCase.registerEmployee(userResponse, 2L));
    }

    @Test
    void testRegisterEmployeeOwnerNotFound() {
        // Given
        UserResponse userResponse = new UserResponse(1L, "username", "password", "firstName", "lastName", "email", LocalDate.of(2020, 1, 1), "role");
        Restaurant restaurant = new Restaurant(2L, "Sample Restaurant", "123456789", "123 Main Street", "555-1234", "https://sample.com/logo.png", 1L);
        restaurant.setOwnerId(2L);

        when(restaurantPersistencePort.findById(any(Long.class))).thenReturn(Optional.of(restaurant));
        when(securityPersistencePort.getIdUser()).thenReturn(1L);

        // When & Then
        assertThrows(OwnerNotFoundException.class, () -> userUseCase.registerEmployee(userResponse, 2L));
    }



}
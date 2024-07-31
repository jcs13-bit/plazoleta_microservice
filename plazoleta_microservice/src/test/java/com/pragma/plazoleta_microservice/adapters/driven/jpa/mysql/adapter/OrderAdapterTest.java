package com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.plazoleta_microservice.adapters.client.IUserClient;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.exception.OrderStateChangeException;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.exception.RoleNotCorrectException;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.mapper.IOrderEntityMapper;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.repository.IDishRepository;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.repository.IOrderRepository;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.repository.IRestaurantRepository;
import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.response.UserResponse;
import com.pragma.plazoleta_microservice.domain.exceptions.DishNotFoundException;
import com.pragma.plazoleta_microservice.domain.exceptions.RestaurantNotFoundException;
import com.pragma.plazoleta_microservice.domain.exceptions.UserNotFoundException;
import com.pragma.plazoleta_microservice.domain.model.DishQuantify;
import com.pragma.plazoleta_microservice.domain.model.Order;
import com.pragma.plazoleta_microservice.domain.spi.ISecurityPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class OrderAdapterTest {
    @Mock
    private IOrderRepository orderRepository;

    @Mock
    private IOrderEntityMapper orderEntityMapper;

    @Mock
    private IRestaurantRepository restaurantRepository;

    @Mock
    private IUserClient userClient;

    @Mock
    private IDishRepository dishRepository;

    @InjectMocks
    private OrderAdapter orderAdapter;

    @Mock
    private ISecurityPersistencePort securityPersistencePort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSaveOrderSuccessfully() {
        // Arrange
        Order order = new Order(null, 1L, LocalDate.now(), "PENDING", 2L, 3L, Arrays.asList(new DishQuantify(1L, 2), new DishQuantify(2L, 1)));

        UserResponse chefResponse = new UserResponse(2L, "EMPLOYEE", "test","test@gmail.com","1234","+573163718182",LocalDate.now(),null,"EMPLOYEE");

        UserResponse clientResponse = new UserResponse(1L, "CLIENT", "test","test@gmail.com","1234","+573163718182",LocalDate.now(),null,"CLIENT");


        when(userClient.getUserById(2L)).thenReturn(chefResponse);
        when(userClient.getUserById(1L)).thenReturn(clientResponse);

        when(restaurantRepository.findById(3L)).thenReturn(Optional.of(new RestaurantEntity()));
        when(orderRepository.findByClientIdAndStatusIn(eq(1L), anyList())).thenReturn(new ArrayList<>());

        DishEntity dishEntity1 = new DishEntity();
        dishEntity1.setId(1L);

        DishEntity dishEntity2 = new DishEntity();
        dishEntity2.setId(2L);

        when(dishRepository.findByRestaurantIdAndId(3L, 1L)).thenReturn(Optional.of(dishEntity1));
        when(dishRepository.findByRestaurantIdAndId(3L, 2L)).thenReturn(Optional.of(dishEntity2));

        // Act
        orderAdapter.saveOrder(order);

        // Assert
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
    }

    @Test
    void testSaveOrderThrowsUserNotFoundException() {
        // Arrange
        Order order = new Order(null, 1L, LocalDate.now(), "PENDING", 2L, 3L, Arrays.asList(new DishQuantify(1L, 2), new DishQuantify(2L, 1)));

        when(userClient.getUserById(2L)).thenReturn(null);

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> orderAdapter.saveOrder(order));
    }

    @Test
    void testSaveOrderThrowsRoleNotCorrectException() {
        // Arrange
        Order order = new Order(null, 1L, LocalDate.now(), "PENDING", 2L, 3L, Arrays.asList(new DishQuantify(1L, 2), new DishQuantify(2L, 1)));


        UserResponse chefResponse = new UserResponse(1L, "CLIENT", "test","test@gmail.com","1234","+573163718182",LocalDate.now(),null,"CLIENT");


        when(userClient.getUserById(2L)).thenReturn(chefResponse);

        // Act & Assert
        assertThrows(RoleNotCorrectException.class, () -> orderAdapter.saveOrder(order));
    }

    @Test
    void testSaveOrderThrowsRestaurantNotFoundException() {
        // Arrange
        Order order = new Order(null, 1L, LocalDate.now(), "PENDING", 2L, 3L, Arrays.asList(new DishQuantify(1L, 2), new DishQuantify(2L, 1)));

        UserResponse chefResponse = new UserResponse(2L, "EMPLOYEE", "test","test@gmail.com","1234","+573163718182",LocalDate.now(),null,"EMPLOYEE");

        UserResponse clientResponse = new UserResponse(1L, "CLIENT", "test","test@gmail.com","1234","+573163718182",LocalDate.now(),null,"CLIENT");




        when(userClient.getUserById(2L)).thenReturn(chefResponse);
        when(userClient.getUserById(1L)).thenReturn(clientResponse);

        when(restaurantRepository.findById(3L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RestaurantNotFoundException.class, () -> orderAdapter.saveOrder(order));
    }

    @Test
    void testSaveOrderThrowsOrderStateChangeException() {
        // Arrange
        Order order = new Order(null, 1L, LocalDate.now(), "PENDING", 2L, 3L, Arrays.asList(new DishQuantify(1L, 2), new DishQuantify(2L, 1)));


        UserResponse chefResponse = new UserResponse(2L, "EMPLOYEE", "test","test@gmail.com","1234","+573163718182",LocalDate.now(),null,"EMPLOYEE");

        UserResponse clientResponse = new UserResponse(1L, "CLIENT", "test","test@gmail.com","1234","+573163718182",LocalDate.now(),null,"CLIENT");


        when(userClient.getUserById(2L)).thenReturn(chefResponse);
        when(userClient.getUserById(1L)).thenReturn(clientResponse);

        when(restaurantRepository.findById(3L)).thenReturn(Optional.of(new RestaurantEntity()));
        when(orderRepository.findByClientIdAndStatusIn(eq(1L), anyList())).thenReturn(Arrays.asList(new OrderEntity()));

        // Act & Assert
        assertThrows(OrderStateChangeException.class, () -> orderAdapter.saveOrder(order));
    }

    @Test
    void testSaveOrderThrowsDishNotFoundException() {
        // Arrange
        Order order = new Order(null, 1L, LocalDate.now(), "PENDING", 2L, 3L, Arrays.asList(new DishQuantify(1L, 2), new DishQuantify(2L, 1)));


        UserResponse chefResponse = new UserResponse(2L, "EMPLOYEE", "test","test@gmail.com","1234","+573163718182",LocalDate.now(),null,"EMPLOYEE");

        UserResponse clientResponse = new UserResponse(1L, "CLIENT", "test","test@gmail.com","1234","+573163718182",LocalDate.now(),null,"CLIENT");



        when(userClient.getUserById(2L)).thenReturn(chefResponse);
        when(userClient.getUserById(1L)).thenReturn(clientResponse);

        when(restaurantRepository.findById(3L)).thenReturn(Optional.of(new RestaurantEntity()));
        when(orderRepository.findByClientIdAndStatusIn(eq(1L), anyList())).thenReturn(new ArrayList<>());

        when(dishRepository.findByRestaurantIdAndId(3L, 1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DishNotFoundException.class, () -> orderAdapter.saveOrder(order));
    }




}
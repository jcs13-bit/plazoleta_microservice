package com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.plazoleta_microservice.adapters.client.IUserClient;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.entity.EmployeeWithRestaurantEntity;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.exception.OrderStateChangeException;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.exception.RoleNotCorrectException;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.mapper.IEmployeeWithRestaurantEntityMapper;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.mapper.IOrderEntityMapper;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.repository.IDishRepository;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.repository.IEmployeeWithRestaurantRepository;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.repository.IOrderRepository;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.repository.IRestaurantRepository;
import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.response.UserResponse;
import com.pragma.plazoleta_microservice.domain.exceptions.DishNotFoundException;
import com.pragma.plazoleta_microservice.domain.exceptions.RestaurantNotFoundException;
import com.pragma.plazoleta_microservice.domain.exceptions.UserNotFoundException;
import com.pragma.plazoleta_microservice.domain.model.DishQuantify;
import com.pragma.plazoleta_microservice.domain.model.EmployeeWithRestaurant;
import com.pragma.plazoleta_microservice.domain.model.Order;
import com.pragma.plazoleta_microservice.domain.spi.ISecurityPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    @Mock
    private IEmployeeWithRestaurantRepository employeeWithRestaurantRepository;

    @Mock
    private IEmployeeWithRestaurantEntityMapper employeeWithRestaurantEntityMapper;




    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        orderAdapter = new OrderAdapter(orderRepository, orderEntityMapper, restaurantRepository, userClient, dishRepository, securityPersistencePort, employeeWithRestaurantRepository);
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

    @Test
    void testGetOrders() {
        // Arrange
        Long restaurantId = 1L;
        OrderEntity orderEntity1 = new OrderEntity();
        OrderEntity orderEntity2 = new OrderEntity();
        List<OrderEntity> orderEntities = Arrays.asList(orderEntity1, orderEntity2);
        Page<OrderEntity> orderEntityPage = new PageImpl<>(orderEntities);

        when(securityPersistencePort.getIdUser()).thenReturn(1L);

        // El repository debe devolver un Optional<EmployeeWithRestaurant>
        EmployeeWithRestaurantEntity employeeWithRestaurantEntity = new EmployeeWithRestaurantEntity(1L, 2L, restaurantId);
        when(employeeWithRestaurantRepository.findRestaurantIdByEmployee(anyLong()))
                .thenReturn(Optional.of(employeeWithRestaurantEntity));

        // El repository de órdenes debe devolver un Page<OrderEntity>
        when(orderRepository.findByRestaurantIdAndStatus(eq(restaurantId), eq("status"), any(Pageable.class)))
                .thenReturn(orderEntityPage);

        // El mapper debe convertir la lista de entidades a una lista de modelos
        List<Order> mappedOrders = Arrays.asList(new Order(1L,2L, LocalDate.now(), "PENDING", 1L, 2L, Arrays.asList(new DishQuantify(1L, 2), new DishQuantify(2L, 1))), new Order(2L,2L, LocalDate.now(), "PENDING", 1L, 2L, Arrays.asList(new DishQuantify(1L, 2), new DishQuantify(2L, 1))));
        when(orderEntityMapper.toModelList(orderEntities)).thenReturn(mappedOrders);

        // Act
        List<Order> orders = orderAdapter.getOrders(1, 10, "status");

        // Assert
        assertNotNull(orders);
        assertEquals(2, orders.size());
        verify(orderEntityMapper, times(1)).toModelList(orderEntities);
    }

    @Test
    void testTakeOrder() {
        // Arrange
        // Set up mock behavior for the dependencies
        Long orderId = 1L;
        Long idEmployee = 1L;
        Long restaurantId = 1L;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setRestaurantId(restaurantId); // Set the restaurantId field to a non-null value

        when(securityPersistencePort.getIdUser()).thenReturn(idEmployee);
        EmployeeWithRestaurantEntity employeeWithRestaurantEntity = new EmployeeWithRestaurantEntity(1L, 2L, restaurantId);
        when(employeeWithRestaurantRepository.findRestaurantIdByEmployee(anyLong()))
                .thenReturn(Optional.of(employeeWithRestaurantEntity));
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));

        // Act
        orderAdapter.takeOrder(orderId);

        // Assert
        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, times(1)).save(orderEntity);
    }




}
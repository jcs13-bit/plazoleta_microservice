package com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.plazoleta_microservice.adapters.client.IUserClient;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.exception.ConstantsAdapter;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.exception.OrderStateChangeException;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.exception.RoleNotCorrectException;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.mapper.IOrderEntityMapper;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.repository.IDishRepository;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.repository.IOrderRepository;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.repository.IRestaurantRepository;
import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.response.OwnerResponse;
import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.response.UserResponse;
import com.pragma.plazoleta_microservice.adapters.util.services.exception.DataNotFoundException;
import com.pragma.plazoleta_microservice.domain.exceptions.ConstantsDomain;
import com.pragma.plazoleta_microservice.domain.exceptions.DishNotFoundException;
import com.pragma.plazoleta_microservice.domain.exceptions.RestaurantNotFoundException;
import com.pragma.plazoleta_microservice.domain.exceptions.UserNotFoundException;
import com.pragma.plazoleta_microservice.domain.model.DishQuantify;
import com.pragma.plazoleta_microservice.domain.model.Order;
import com.pragma.plazoleta_microservice.domain.spi.IOrderPersistencePort;
import com.pragma.plazoleta_microservice.domain.spi.ISecurityPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RequiredArgsConstructor
public class OrderAdapter  implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;

    private final IRestaurantRepository restaurantRepository;

    private final IUserClient userClient;

    private final IDishRepository dishRepository;





    @Override
    public void saveOrder(Order order) {

        UserResponse userResponse = userClient.getUserById(order.getChefId());
        if (userResponse == null) {
            throw new UserNotFoundException(ConstantsDomain.USER_NOT_FOUND);
        }
        if(!userResponse.getRole().equals("EMPLOYEE")) {
                throw new RoleNotCorrectException(ConstantsAdapter.ROLE_BAD);
        }
        UserResponse userResponseClient = userClient.getUserById(order.getClientId());
        if (userResponseClient == null) {
            throw new UserNotFoundException(ConstantsDomain.USER_NOT_FOUND);
        }
        if(!userResponseClient.getRole().equals("CLIENT")) {
            throw new RoleNotCorrectException(ConstantsAdapter.ROLE_BAD);
        }
        restaurantRepository.findById(order.getRestaurantId()).orElseThrow(() -> new RestaurantNotFoundException(ConstantsDomain.RESTAURANT_NOT_FOUND));
        ArrayList<String> allowed = new ArrayList<>(Arrays.asList(ConstantsAdapter.STATUS_PENDING, ConstantsAdapter.STATUS_READY, ConstantsAdapter.STATUS_PREPARATION));
        List<OrderEntity> existingOrders = orderRepository.findByClientIdAndStatusIn(order.getClientId(), allowed);
        if (!existingOrders.isEmpty()) {
            throw new OrderStateChangeException(ConstantsAdapter.ORDER_STATE_CHANGE_EXCEPTION);
        }
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setClientId(order.getClientId());
        orderEntity.setChefId(order.getChefId());
        orderEntity.setRestaurantId(order.getRestaurantId());
        orderEntity.setStatus(ConstantsAdapter.STATUS_PENDING);
        orderEntity.setDate(order.getDate());
        List<DishEntity> dishEntities = new ArrayList<>();
        for (DishQuantify dishQuantify : order.getDishesQuantify() ) {
            DishEntity dishEntity = dishRepository.findByRestaurantIdAndId(order.getRestaurantId(), dishQuantify.getDishId())
                    .orElseThrow(() -> new DishNotFoundException(ConstantsAdapter.DISH_NOT_FOUND_EXCEPTION_MESSAGE));
            for (int i = 0; i < dishQuantify.getQuantity(); i++) {
                dishEntities.add(dishEntity);
            }
        }
        orderEntity.setDishes(dishEntities);
        orderRepository.save(orderEntity);
    }
}

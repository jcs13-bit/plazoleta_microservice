package com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.plazoleta_microservice.adapters.client.ISmsClient;
import com.pragma.plazoleta_microservice.adapters.client.IUserClient;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.exception.*;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.mapper.IOrderEntityMapper;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.repository.IDishRepository;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.repository.IEmployeeWithRestaurantRepository;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.repository.IOrderRepository;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.repository.IRestaurantRepository;
import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.response.UserResponse;
import com.pragma.plazoleta_microservice.adapters.util.services.exception.OrderStatusEnum;
import com.pragma.plazoleta_microservice.domain.exceptions.ConstantsDomain;
import com.pragma.plazoleta_microservice.domain.exceptions.DishNotFoundException;
import com.pragma.plazoleta_microservice.domain.exceptions.RestaurantNotFoundException;
import com.pragma.plazoleta_microservice.domain.exceptions.UserNotFoundException;
import com.pragma.plazoleta_microservice.domain.model.DishQuantify;
import com.pragma.plazoleta_microservice.domain.model.Order;
import com.pragma.plazoleta_microservice.domain.spi.IOrderPersistencePort;
import com.pragma.plazoleta_microservice.domain.spi.ISecurityPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RequiredArgsConstructor
public class OrderAdapter  implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;

    private final IRestaurantRepository restaurantRepository;

    private final IUserClient userClient;

    private final ISmsClient smsClient;

    private final IDishRepository dishRepository;

    private final ISecurityPersistencePort securityPersistencePort;

    private final IEmployeeWithRestaurantRepository employeeWithRestaurantRepository;







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
        orderEntity.setDate(LocalDate.now());
        orderRepository.save(orderEntity);
    }

    @Override
    public List<Order> getOrders(Integer page, Integer size, String status) {

        Long idEmployee = securityPersistencePort.getIdUser();
        Long restaurantId = employeeWithRestaurantRepository.findRestaurantIdByEmployee(idEmployee).get().getRestaurantId();

        Pageable pagination = PageRequest.of(page, size);
        List<OrderEntity> orderEntities = orderRepository.findByRestaurantIdAndStatus(restaurantId, status,  pagination).getContent();

        return orderEntityMapper.toModelList(orderEntities);
    }
    @Override
    public void takeOrder(Long orderId) {
        Long idEmployee = securityPersistencePort.getIdUser();
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(ConstantsAdapter.ORDER_NOT_FOUND));

        if(!orderEntity.getRestaurantId().equals(employeeWithRestaurantRepository.findRestaurantIdByEmployee(idEmployee).get().getRestaurantId())) {
            throw new OrderNotFoundException(ConstantsAdapter.ORDER_NOT_FOUND);
        }
        orderEntity.setChefId(idEmployee);
        orderEntity.setStatus(nextStatus(orderEntity.getStatus()));
        String phone = userClient.getUserById(orderEntity.getClientId()).getCellphone();
        smsClient.sendCode(phone);
        orderRepository.save(orderEntity);

    }

    @Override
    public String readyOrder(Long idOrder) {

        OrderEntity orderEntity = orderRepository.findById(idOrder).orElseThrow(() -> new OrderNotFoundException(ConstantsAdapter.ORDER_NOT_FOUND));
        orderEntity.setStatus(nextStatus(orderEntity.getStatus()));
        String phone = userClient.getUserById(orderEntity.getClientId()).getCellphone();
        smsClient.sendCode(phone);
        orderRepository.save(orderEntity);

        return smsClient.sendCode(phone).toString();

    }


    private String nextStatus (String status){

        switch (status) {
            case "PENDING":
                return OrderStatusEnum.IN_PROGRESS.toString();
            case "IN_PROGRESS":
                return OrderStatusEnum.READY.toString();
            case "READY":
                return  OrderStatusEnum.DELIVERED.toString();
            default:
                throw new InvalidStatusException(ConstantsAdapter.STATUS_INVALID_EXCEPTION_MESSAGE);
        }
    }
}

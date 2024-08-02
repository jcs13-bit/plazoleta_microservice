package com.pragma.plazoleta_microservice.domain.spi;

import com.pragma.plazoleta_microservice.domain.model.Order;

import java.util.List;

public interface IOrderPersistencePort {

    void saveOrder(Order order);

    List<Order> getOrders(Integer page, Integer size, String status);


    void takeOrder(Long idOrder);

    String readyOrder(Long idOrder);


    String deliverOrder(Long idOrder, String code);


    String cancelOrder(Long idOrder);
}

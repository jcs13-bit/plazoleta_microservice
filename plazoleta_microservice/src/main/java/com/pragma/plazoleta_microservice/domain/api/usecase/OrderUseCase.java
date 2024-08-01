package com.pragma.plazoleta_microservice.domain.api.usecase;

import com.pragma.plazoleta_microservice.domain.api.IOrderServicePort;
import com.pragma.plazoleta_microservice.domain.model.Order;
import com.pragma.plazoleta_microservice.domain.spi.IOrderPersistencePort;

import java.util.List;

public class OrderUseCase implements IOrderServicePort {
    private IOrderPersistencePort orderPersistencePort;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
    }
    @Override
    public void saveOrder(Order order) {
        orderPersistencePort.saveOrder(order);
    }



    @Override
    public List<Order> getOrders(Integer page, Integer size, String status) {

        return orderPersistencePort.getOrders(page, size, status);
    }

    @Override
    public void takeOrder(Long idOrder) {
        orderPersistencePort.takeOrder(idOrder);
    }
    @Override
    public String readyOrder(Long idOrder) {
        return orderPersistencePort.readyOrder(idOrder).toString();

    }

    @Override
    public String deliverOrder(Long idOrder, String code) {
        return orderPersistencePort.deliverOrder(idOrder, code).toString();
    }


}

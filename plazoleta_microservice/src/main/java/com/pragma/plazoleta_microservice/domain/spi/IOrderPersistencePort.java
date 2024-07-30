package com.pragma.plazoleta_microservice.domain.spi;

import com.pragma.plazoleta_microservice.domain.model.Order;

public interface IOrderPersistencePort {

    void saveOrder(Order order);
}

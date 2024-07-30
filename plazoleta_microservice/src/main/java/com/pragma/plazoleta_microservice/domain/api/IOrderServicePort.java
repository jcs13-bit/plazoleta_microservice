package com.pragma.plazoleta_microservice.domain.api;

import com.pragma.plazoleta_microservice.domain.model.Order;

public interface IOrderServicePort {

    void saveOrder(Order order);

}

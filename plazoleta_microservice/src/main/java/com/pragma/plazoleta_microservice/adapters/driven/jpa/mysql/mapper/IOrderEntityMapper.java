package com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.mapper;

import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.plazoleta_microservice.domain.model.DishQuantify;
import com.pragma.plazoleta_microservice.domain.model.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IOrderEntityMapper {
    Order toModel(OrderEntity orderEntity);

    OrderEntity toEntity(Order order);

    List<Order> toModelList(List<OrderEntity> orderEntityList);

    DishQuantify dishEntityToDishQuantify(DishEntity dishEntity);

}

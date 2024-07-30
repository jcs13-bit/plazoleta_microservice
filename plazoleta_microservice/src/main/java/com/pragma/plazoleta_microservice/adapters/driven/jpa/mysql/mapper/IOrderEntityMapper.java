package com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.mapper;

import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.plazoleta_microservice.domain.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IOrderEntityMapper {
    Order toModel(OrderEntity orderEntity);

    OrderEntity toEntity(Order order);


}

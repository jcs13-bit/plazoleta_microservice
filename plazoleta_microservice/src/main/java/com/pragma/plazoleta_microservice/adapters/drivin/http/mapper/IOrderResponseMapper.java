package com.pragma.plazoleta_microservice.adapters.drivin.http.mapper;

import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.response.OrderResponse;
import com.pragma.plazoleta_microservice.domain.model.Order;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring")
public interface IOrderResponseMapper {

    OrderResponse toOrderResponse(Order order);

    List<OrderResponse> toOrderResponseList(List<Order> orderList);
}

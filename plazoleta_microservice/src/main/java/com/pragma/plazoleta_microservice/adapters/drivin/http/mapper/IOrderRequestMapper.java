package com.pragma.plazoleta_microservice.adapters.drivin.http.mapper;

import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.request.AddOrderRequest;
import com.pragma.plazoleta_microservice.domain.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IOrderRequestMapper {

    Order addOrderRequestToOrder(AddOrderRequest addOrderRequest);

}

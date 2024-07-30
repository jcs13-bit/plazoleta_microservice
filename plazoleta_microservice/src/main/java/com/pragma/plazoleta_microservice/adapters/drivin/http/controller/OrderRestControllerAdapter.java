package com.pragma.plazoleta_microservice.adapters.drivin.http.controller;

import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.request.AddOrderRequest;
import com.pragma.plazoleta_microservice.adapters.drivin.http.mapper.IOrderRequestMapper;
import com.pragma.plazoleta_microservice.domain.api.IOrderServicePort;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Validated
public class OrderRestControllerAdapter {

    private final IOrderServicePort orderServicePort;
    private final IOrderRequestMapper orderRequestMapper;

    @PostMapping("/addOrder")
    @Operation(summary = "Create a new order")
    public ResponseEntity<Void> addOrder(@Valid @RequestBody AddOrderRequest request){
        orderServicePort.saveOrder(orderRequestMapper.addOrderRequestToOrder(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
    
    
    
}

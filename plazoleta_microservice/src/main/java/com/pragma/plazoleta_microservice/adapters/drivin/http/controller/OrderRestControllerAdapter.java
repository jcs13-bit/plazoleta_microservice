package com.pragma.plazoleta_microservice.adapters.drivin.http.controller;

import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.request.AddOrderRequest;
import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.response.OrderResponse;
import com.pragma.plazoleta_microservice.adapters.drivin.http.mapper.IOrderRequestMapper;
import com.pragma.plazoleta_microservice.adapters.drivin.http.mapper.IOrderResponseMapper;
import com.pragma.plazoleta_microservice.domain.api.IOrderServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Validated
@SecurityRequirement(name = "bearer-key")
public class OrderRestControllerAdapter {

    private final IOrderServicePort orderServicePort;
    private final IOrderRequestMapper orderRequestMapper;

    private final IOrderResponseMapper orderResponseMapper;

    @PostMapping("/addOrder")
    @Operation(summary = "Create a new order")
    public ResponseEntity<Void> addOrder(@Valid @RequestBody AddOrderRequest request){
        orderServicePort.saveOrder(orderRequestMapper.addOrderRequestToOrder(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/")
    ResponseEntity<List<OrderResponse>> getOrder(@RequestParam Integer page, Integer size, String status) {
        return ResponseEntity.ok(orderResponseMapper.toOrderResponseList(orderServicePort.getOrders(page, size, status)));
    }
    @PreAuthorize("hasRole('EMPLOYEE')")
    @PatchMapping("/takeOrder")
    public ResponseEntity<Void> takeOrder(@RequestParam  Long idOrder) {
        orderServicePort.takeOrder(idOrder);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PatchMapping("/readyOrder")
    public ResponseEntity<Void> readyOrder(@RequestParam  Long idOrder) {
        orderServicePort.takeOrder(idOrder);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PreAuthorize("hasRole('EMPLOYEE')")
    @PatchMapping("/deliverOrder")
    public ResponseEntity<Void> deliverOrder(@RequestParam  Long idOrder, @RequestParam String code) {
        orderServicePort.deliverOrder(idOrder,code);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    
    
    
}

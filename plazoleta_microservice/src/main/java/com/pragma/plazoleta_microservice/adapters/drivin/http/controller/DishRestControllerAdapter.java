package com.pragma.plazoleta_microservice.adapters.drivin.http.controller;

import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.request.AddDishRequest;
import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.request.UpdateDishRequest;
import com.pragma.plazoleta_microservice.adapters.drivin.http.mapper.IDishRequestMapper;
import com.pragma.plazoleta_microservice.domain.api.usecase.IDishServicePort;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/dish")
@RequiredArgsConstructor
@Validated
public class DishRestControllerAdapter {
    private final IDishServicePort dishServicePort;
    private final IDishRequestMapper dishRequestMapper;

    @PostMapping("/addDish")
    @Operation(summary = "Endpoint to add a new dish")
    public ResponseEntity <Void> addDish(@Valid @RequestBody AddDishRequest request){
        dishServicePort.saveDish(dishRequestMapper.addRequestToDish(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PatchMapping("/updateDish")
    @Operation(summary = "Endpoint to update a dish")
    public ResponseEntity <Void> updateDish(@Valid @RequestBody UpdateDishRequest request){
        dishServicePort.updateDish(request.getId(),request.getDescription(),request.getPrice());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

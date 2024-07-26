package com.pragma.plazoleta_microservice.adapters.drivin.http.controller;

import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.request.AddDishRequest;
import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.request.UpdateDishRequest;
import com.pragma.plazoleta_microservice.adapters.drivin.http.mapper.IDishRequestMapper;
import com.pragma.plazoleta_microservice.domain.api.IDishServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/dish")
@RequiredArgsConstructor
@Validated
@SecurityRequirement(name = "bearer-key")
public class DishRestControllerAdapter {
    private final IDishServicePort dishServicePort;
    private final IDishRequestMapper dishRequestMapper;


    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/addDish")
    @Operation(summary = "Endpoint to add a new dish")
    public ResponseEntity <Void> addDish(@Valid @RequestBody AddDishRequest request){
        dishServicePort.saveDish(dishRequestMapper.addRequestToDish(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PreAuthorize("hasRole('OWNER')")
    @PatchMapping("/updateDish")
    @Operation(summary = "Endpoint to update a dish")
    public ResponseEntity <Void> updateDish(@Valid @RequestBody UpdateDishRequest request){
        dishServicePort.updateDish(request.getId(),request.getDescription(),request.getPrice());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PreAuthorize("hasRole('OWNER')")
    @PatchMapping("/changeStatusDish/")
    @Operation(summary = "Endpoint to change the status of a dish")
    public ResponseEntity <Void> changeStatusDish(@RequestParam Long id){
        dishServicePort.changeStatusDish(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }





}

package com.pragma.plazoleta_microservice.adapters.drivin.http.controller;

import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.request.AddRestaurantRequest;
import com.pragma.plazoleta_microservice.adapters.drivin.http.mapper.IRestaurantRequestMapper;
import com.pragma.plazoleta_microservice.domain.api.usecase.IRestaurantServicePort;
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
@RequestMapping("/restaurant")
@RequiredArgsConstructor
@Validated
public class RestaurantRestControllerAdapter {
    private final IRestaurantServicePort restaurantServicePort;

    private final IRestaurantRequestMapper restaurantRequestMapper;


    @PostMapping("/")
    @Operation(summary = "Create a new restaurant")
    public ResponseEntity<Void> addRestaurant(@Valid @RequestBody AddRestaurantRequest request){
        restaurantServicePort.saveRestaurant(restaurantRequestMapper.addRestaurantRequestToRestaurant(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

}

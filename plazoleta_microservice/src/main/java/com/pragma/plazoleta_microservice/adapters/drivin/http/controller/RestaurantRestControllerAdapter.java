package com.pragma.plazoleta_microservice.adapters.drivin.http.controller;

import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.request.AddRestaurantRequest;
import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.response.RestaurantResponse;
import com.pragma.plazoleta_microservice.adapters.drivin.http.mapper.IRestaurantRequestMapper;
import com.pragma.plazoleta_microservice.adapters.drivin.http.mapper.IRestaurantResponseMapper;
import com.pragma.plazoleta_microservice.domain.api.IRestaurantServicePort;
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
@RequestMapping("/restaurant")
@RequiredArgsConstructor
@Validated
@SecurityRequirement(name = "bearer-key")
public class RestaurantRestControllerAdapter {
    private final IRestaurantServicePort restaurantServicePort;

    private final IRestaurantRequestMapper restaurantRequestMapper;

    private final IRestaurantResponseMapper restaurantResponseMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addRestaurant")
    @Operation(summary = "Create a new restaurant")
    public ResponseEntity<Void> addRestaurant(@Valid @RequestBody AddRestaurantRequest request){
        restaurantServicePort.saveRestaurant(restaurantRequestMapper.addRestaurantRequestToRestaurant(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
    @Operation(summary = "List all restaurants")
    @GetMapping("/")
     public ResponseEntity<List<RestaurantResponse>> getAllRestaurants(
             @RequestParam(defaultValue = "0") Integer page,
             @RequestParam(defaultValue = "10") Integer size){
         return ResponseEntity.ok(restaurantResponseMapper.toRestaurantResponseList(restaurantServicePort.getAllRestaurants(page, size)));
     }










}

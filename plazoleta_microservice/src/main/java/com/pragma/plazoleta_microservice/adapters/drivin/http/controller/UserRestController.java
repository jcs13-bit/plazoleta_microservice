package com.pragma.plazoleta_microservice.adapters.drivin.http.controller;



import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.request.AddEmployeeRequest;
import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.response.UserResponse;
import com.pragma.plazoleta_microservice.adapters.drivin.http.mapper.IEmployeeMapperRequest;
import com.pragma.plazoleta_microservice.domain.api.usecase.UserUseCase;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@SecurityRequirement(name = "bearer-key")
public class UserRestController {
    private final UserUseCase userService;

    private final IEmployeeMapperRequest userMapper;

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/employee")
    public void registerEmployee(@RequestBody AddEmployeeRequest employee) {
        Long idRestaurant = employee.getRestaurantId();
        UserResponse user = userMapper.toUserResponse(employee);
        userService.registerEmployee(user, idRestaurant);
    }



}

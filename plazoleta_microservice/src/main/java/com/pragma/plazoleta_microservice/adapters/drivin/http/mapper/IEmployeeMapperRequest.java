package com.pragma.plazoleta_microservice.adapters.drivin.http.mapper;

import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.request.AddEmployeeRequest;
import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IEmployeeMapperRequest {
    UserResponse toUserResponse(AddEmployeeRequest addEmployeeRequest);
}

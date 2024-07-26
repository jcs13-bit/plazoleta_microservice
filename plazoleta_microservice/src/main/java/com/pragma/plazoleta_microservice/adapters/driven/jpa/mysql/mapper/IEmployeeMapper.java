package com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.mapper;

import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.request.AddEmployeeRequest;
import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.response.UserResponse;

public interface IEmployeeMapper {

    UserResponse toUserResponse(AddEmployeeRequest userResponse);
}

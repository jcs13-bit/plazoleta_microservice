package com.pragma.plazoleta_microservice.adapters.drivin.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@AllArgsConstructor
@Data
public class UserResponse {
    private final Long id;
    private final String name;
    private final String lastName;
    private final String email;
    private final String docNumber;
    private final String cellphone;
    private final LocalDate birthDate;
    private final String password;


}

package com.pragma.plazoleta_microservice.adapters.drivin.http.dto.request;

import com.pragma.plazoleta_microservice.adapters.drivin.http.exceptions.DtoConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class AddRestaurantRequest {

    @NotBlank
    @Pattern(regexp = ".*[a-zA-Z]+.*", message = DtoConstants.FIELD_NAME_IS_VALID_MESSAGE)
    @Size(max = 50, message = DtoConstants.FIELD_NAME_SIZE_MESSAGE)
    private String name;

    @NotBlank
    @Pattern(regexp = "\\d+", message = DtoConstants.FIELD_NIT_NUMBER_IS_VALID_MESSAGE)
    private String nit;


    private Long ownerId;

    @NotBlank
    private String address;
    @NotBlank
    @Pattern(regexp = "\\+?\\d{13}", message = DtoConstants.FIELD_CELLPHONE_IS_VALID_MESSAGE)
    private String phone;
    @NotBlank
    private String urlLogo;

}

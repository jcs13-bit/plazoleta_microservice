package com.pragma.plazoleta_microservice.adapters.client;

import com.pragma.plazoleta_microservice.adapters.drivin.http.dto.response.OwnerResponse;
import com.pragma.plazoleta_microservice.configuration.client.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "http://localhost:8090", configuration = FeignClientConfig.class)
public interface IUserClient {
    @GetMapping(value = "/user/getRoles", consumes = MediaType.APPLICATION_JSON_VALUE)
    OwnerResponse getRoleNameByUserId(@RequestParam("id") Long userId);
    //getRoleNameByUserId(Long userId);
}

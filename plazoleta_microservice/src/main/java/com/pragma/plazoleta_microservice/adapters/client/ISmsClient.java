package com.pragma.plazoleta_microservice.adapters.client;


import com.pragma.plazoleta_microservice.configuration.client.FeignClientConfig;
import com.pragma.plazoleta_microservice.util.StatusVerificationEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "sms-service", url = "http://localhost:8092", configuration = FeignClientConfig.class)
public interface ISmsClient {
    @PostMapping(value = "/sms/sendCode")
    StatusVerificationEnum sendCode(@RequestParam("phone") String phone);
}

package com.pragma.plazoleta_microservice.configuration;

import com.pragma.plazoleta_microservice.adapters.client.IUserClient;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.adapter.DishAdapter;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.adapter.RestaurantAdapter;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.mapper.IDishEntityMapper;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.mapper.IRestaurantEntityMapper;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.repository.IDishRepository;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.repository.IRestaurantRepository;
import com.pragma.plazoleta_microservice.domain.api.usecase.DishUseCase;
import com.pragma.plazoleta_microservice.domain.api.usecase.IDishServicePort;
import com.pragma.plazoleta_microservice.domain.api.usecase.IRestaurantServicePort;
import com.pragma.plazoleta_microservice.domain.api.usecase.RestaurantUseCase;
import com.pragma.plazoleta_microservice.domain.spi.IDishPersistencePort;
import com.pragma.plazoleta_microservice.domain.spi.IRestaurantPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IRestaurantEntityMapper restaurantEntityMapper;

    private final IRestaurantRepository restaurantRepository;

    private final IDishEntityMapper dishEntityMapper;

    private final IDishRepository dishRepository;

    private final IUserClient userClient;



    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort() {

        return new RestaurantAdapter(restaurantRepository, restaurantEntityMapper);

    }
    @Bean
    public IRestaurantServicePort restaurantServicePort(IRestaurantPersistencePort restaurantPersistencePort,IUserClient userClient){
        return new RestaurantUseCase(restaurantPersistencePort,userClient);
    }
    @Bean
    public IDishPersistencePort dishPersistencePort() {
        return new DishAdapter(dishRepository, dishEntityMapper);
    }
    @Bean
    public IDishServicePort dishServicePort(IDishPersistencePort dishPersistencePort){
        return new DishUseCase(dishPersistencePort);
    }


}

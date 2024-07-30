package com.pragma.plazoleta_microservice.configuration;

import com.pragma.plazoleta_microservice.adapters.client.IUserClient;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.adapter.*;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.mapper.IDishEntityMapper;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.mapper.IEmployeeWithRestaurantEntityMapper;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.mapper.IOrderEntityMapper;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.mapper.IRestaurantEntityMapper;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.repository.IDishRepository;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.repository.IEmployeeWithRestaurantRepository;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.repository.IOrderRepository;
import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.repository.IRestaurantRepository;
import com.pragma.plazoleta_microservice.domain.api.IOrderServicePort;
import com.pragma.plazoleta_microservice.domain.api.usecase.DishUseCase;
import com.pragma.plazoleta_microservice.domain.api.IDishServicePort;
import com.pragma.plazoleta_microservice.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta_microservice.domain.api.usecase.OrderUseCase;
import com.pragma.plazoleta_microservice.domain.api.usecase.RestaurantUseCase;
import com.pragma.plazoleta_microservice.domain.api.usecase.UserUseCase;
import com.pragma.plazoleta_microservice.domain.spi.*;
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


    private final IEmployeeWithRestaurantRepository employeeWithRestaurantRepository;

    private final IEmployeeWithRestaurantEntityMapper employeeWithRestaurantEntityMapper;

    private final IOrderEntityMapper orderEntityMapper;
    private final IOrderRepository orderRepository;




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
    public IDishServicePort dishServicePort(IDishPersistencePort dishPersistencePort,ISecurityPersistencePort securityPersistencePort ,IRestaurantPersistencePort restaurantPersistencePort) {
        return new DishUseCase(dishPersistencePort,securityPersistencePort,restaurantPersistencePort);
    }
    @Bean
    public ISecurityPersistencePort securityPersistencePort() {
        return new SecurityAdapter();
    }

    @Bean
    public IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort() {
        return new EmployeeRestaurantAdapter(employeeWithRestaurantRepository, employeeWithRestaurantEntityMapper);
    }
    @Bean
    public UserUseCase userUseCase(IUserClient userClient,
                                   IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort,
                                   ISecurityPersistencePort securityPersistencePort,
                                   IRestaurantPersistencePort restaurantPersistencePort) {
        return new UserUseCase( employeeRestaurantPersistencePort,userClient, securityPersistencePort, restaurantPersistencePort);
    }
    @Bean
    public IOrderPersistencePort orderPersistencePort() {
        return new OrderAdapter(orderRepository, orderEntityMapper,restaurantRepository,userClient,dishRepository);

    }
    @Bean
    public IOrderServicePort orderServicePort(){

        return new OrderUseCase(orderPersistencePort());
    }



}

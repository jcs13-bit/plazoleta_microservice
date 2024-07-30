package com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.repository;

import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findById(Long orderId);
    List<OrderEntity> findByClientIdAndStatusIn(Long ClientId, List<String> statuses);

}

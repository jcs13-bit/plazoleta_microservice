package com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.repository;

import com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.entity.EmployeeWithRestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IEmployeeWithRestaurantRepository extends JpaRepository<EmployeeWithRestaurantEntity, Long> {

    @Query("SELECT e FROM EmployeeWithRestaurantEntity e WHERE e.userId = :userId")
    Optional< EmployeeWithRestaurantEntity> findRestaurantIdByEmployee(Long userId);






}

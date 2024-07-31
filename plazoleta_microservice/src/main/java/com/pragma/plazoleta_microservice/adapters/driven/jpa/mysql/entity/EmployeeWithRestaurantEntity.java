package com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "employee_with_restaurant")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
public class EmployeeWithRestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = false)
    private Long userId;


    @Column(nullable = false, unique = false)
    private Long restaurantId;

}

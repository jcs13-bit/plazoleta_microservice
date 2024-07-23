package com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "restaurant")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,  length = 50)
    private String name;

    @Column(nullable = false,  length = 25)
    private String nit;

    @Column(nullable = false,  length = 50)
    private String address;

    @Column(nullable = false,  length = 14)
    private String phone;





    @Column(nullable = false,  length = 50)
    private String urlLogo;

    @Column
    private Long ownerId;



}

package com.pragma.plazoleta_microservice.adapters.driven.jpa.mysql.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "dish")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,  length = 50)
    private String name;

    @Column(nullable = false,  length = 250)
    private String description;

    @Column(nullable = false,  length = 250)
    private String urlImage;

    @Column(nullable = false,  length = 25)
    private Integer price;

    @Column(nullable = false)
    private Long categoryId;

    @Column(nullable = false)
    private Long restaurantId;

    @Column(nullable = false)
    private Boolean active = true;



}

package com.pragma.plazoleta_microservice.domain.model;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private final Long id;
    private final Long ClientId;
    private final LocalDate date;
    private  String status;

    private final Long chefId;
    private final Long restaurantId;

    private List<DishQuantify> dishesQuantify;

    private List<Dish> dishes;

    public Order(Long id, Long clientId, LocalDate date, String status, Long chefId, Long restaurantId, List<DishQuantify> dishesQuantify) {
        this.id = id;
        ClientId = clientId;
        this.date = date;
        this.status = status;
        this.chefId = chefId;
        this.restaurantId = restaurantId;
        this.dishesQuantify = dishesQuantify;
    }

    public Long getId() {
        return id;
    }

    public Long getClientId() {
        return ClientId;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public Long getChefId() {
        return chefId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DishQuantify> getDishesQuantify() {
        return dishesQuantify;
    }

    public void setDishesQuantify(List<DishQuantify> dishesQuantify) {
        this.dishesQuantify = dishesQuantify;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
}

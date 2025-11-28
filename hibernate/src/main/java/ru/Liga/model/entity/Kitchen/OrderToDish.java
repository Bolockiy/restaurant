package ru.Liga.model.entity.Kitchen;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_to_dish", schema = "kitchen")
@IdClass(OrderToDishId.class)
public class OrderToDish {

    @Id
    private Long kitchenOrderId;

    @Id
    private Long dishId;

    private Long dishesNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kitchen_order_id", insertable = false, updatable = false)
    private KitchenOrder kitchenOrder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dish_id", insertable = false, updatable = false)
    private Dish dish;
}

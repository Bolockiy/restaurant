package ru.Liga.waiter.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "menu", schema = "waiter")
public class Menu {

    @Id
    private Long id;

    @Column(name = "dish_name")
    private String dishName;

    @Column(name = "dish_cost")
    private BigDecimal dishCost;

    @OneToMany(mappedBy = "menu")
    private List<OrderPositions> orderPositions;
}

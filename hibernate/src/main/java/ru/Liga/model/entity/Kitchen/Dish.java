package ru.Liga.model.entity.Kitchen;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "dish", schema = "kitchen")
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dishId;

    private Long balance;

    private String shortName;

    private String dishComposition;


    @OneToMany(mappedBy = "dish", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderToDish> orderToDishes;
}

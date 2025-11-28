package ru.Liga.model.entity.Waiter;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_positions", schema = "waiter")
public class OrderPositions {

    @Id
    @Column(name = "composition_id")
    private Long compositionId;

    @Column(name = "dish_num")
    private Long dishNum;

    @ManyToOne
    @JoinColumn(name = "order_no")
    private WaiterOrder order;

    @ManyToOne
    @JoinColumn(name = "menu_position_id")
    private Menu menu;
}

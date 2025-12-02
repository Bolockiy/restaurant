package ru.Liga.waiter.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_positions", schema = "waiter")
@Getter
@Setter
public class OrderPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "composition_id_seq")
    @SequenceGenerator(
            name = "composition_id_seq",
            sequenceName = "waiter.order_positions_composition_id_seq",
            allocationSize = 1
    )
    @Column(name = "composition_id")
    private Long id;

    @Column(name = "dish_num", nullable = false)
    private Long dishNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_no", nullable = false)
    private WaiterOrder order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_position_id", nullable = false)
    private Menu menu;
}

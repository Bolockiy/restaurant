package ru.Liga.waiter.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "waiter_order", schema = "waiter")
public class WaiterOrder {

    @Id
    @Column(name = "order_no")
    private Long orderNo;

    private String status;

    @Column(name = "create_dttm")
    private OffsetDateTime createDttm;

    @ManyToOne
    @JoinColumn(name = "waiter_id")
    private WaiterAccount waiterAccount;

    @Column(name = "table_no")
    private String tableNo;

    @OneToMany(mappedBy = "order")
    private List<OrderPositions> orderPositions;

    @OneToOne(mappedBy = "order")
    private Payment payment;
}

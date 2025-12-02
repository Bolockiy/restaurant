package ru.Liga.waiter.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "payment", schema = "waiter")
@Getter
@Setter
public class Payment {

    @Id
    @Column(name = "order_no")
    private Long id;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "payment_date")
    private OffsetDateTime paymentDate;

    @Column(name = "payment_sum")
    private BigDecimal paymentSum;

    @OneToOne
    @MapsId
    @JoinColumn(name = "order_no")
    private WaiterOrder order;
}

package ru.Liga.waiter.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "payment", schema = "waiter")
public class Payment {

    @Id
    @Column(name = "order_no")
    private Long orderNo;

    @OneToOne
    @MapsId
    @JoinColumn(name = "order_no")
    private WaiterOrder order;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "payment_date")
    private OffsetDateTime paymentDate;

    @Column(name = "payment_sum")
    private BigDecimal paymentSum;
}

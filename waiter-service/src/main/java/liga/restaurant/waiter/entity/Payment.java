package liga.restaurant.waiter.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "payment")
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
    @JoinColumn(name = "order_no")
    @JsonBackReference
    private WaiterOrder order;
}

package liga.restaurant.waiter.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import liga.restaurant.dto.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "waiter_order")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WaiterOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_no")
    private Long id;

    @Column(nullable = false)
    private OrderStatus status;

    @Column(name = "create_dttm", nullable = false)
    private OffsetDateTime createDttm;

    @Column(name = "table_no", nullable = false)
    private String tableNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "waiter_id", nullable = false)
    @JsonBackReference
    private WaiterAccount waiter;

}

package ru.Liga.waiter.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "waiter_order", schema = "waiter")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class WaiterOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "waiter_order_seq")
    @SequenceGenerator(
            name = "waiter_order_seq",
            sequenceName = "waiter.waiter_order_order_no_seq",
            allocationSize = 1
    )
    @Column(name = "order_no")
    private Long id;

    @Column(nullable = false)
    private String status;

    @Column(name = "create_dttm", nullable = false)
    private OffsetDateTime createDttm;

    @Column(name = "table_no", nullable = false)
    private String tableNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "waiter_id", nullable = false)
    @JsonBackReference
    private WaiterAccount waiter;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderPosition> positions;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Payment payment;
}

package ru.Liga.model.entity.Kitchen;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "kitchen_order", schema = "kitchen")
public class KitchenOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kitchenOrderId;

    private Long waiterOrderNo;

    private String status;

    private OffsetDateTime createDttm;

    @OneToMany(mappedBy = "kitchenOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderToDish> orderToDishes;
}

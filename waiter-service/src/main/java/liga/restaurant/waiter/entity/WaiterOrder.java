package liga.restaurant.waiter.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "waiter_order")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Schema(description = "Сущность заказа официанта")
public class WaiterOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_no")
    @Schema(description = "Уникальный номер заказа", example = "101")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Статус заказа", example = "CREATED")
    private String status;

    @Column(name = "create_dttm", nullable = false)
    @Schema(description = "Дата и время создания заказа",
            example = "2024-01-20T14:52:00+03:00")
    private OffsetDateTime createDttm;

    @Column(name = "table_no", nullable = false)
    @Schema(description = "Номер стола", example = "A12")
    private String tableNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "waiter_id", nullable = false)
    @JsonBackReference
    @Schema(description = "Официант, оформивший заказ")
    private WaiterAccount waiter;

}

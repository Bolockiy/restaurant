package liga.restaurant.waiter.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "payment", schema = "waiter")
@Getter
@Setter
@Schema(description = "Сущность оплаты заказа официанта")
public class Payment {

    @Id
    @Column(name = "order_no")
    @Schema(description = "Номер заказа", example = "101")
    private Long id;

    @Column(name = "payment_type")
    @Schema(description = "Тип оплаты", example = "CASH / CARD")
    private String paymentType;

    @Column(name = "payment_date")
    @Schema(description = "Дата и время оплаты")
    private OffsetDateTime paymentDate;

    @Column(name = "payment_sum")
    @Schema(description = "Сумма оплаты", example = "1500.50")
    private BigDecimal paymentSum;

    @OneToOne
    @JoinColumn(name = "order_no")
    @JsonBackReference
    @Schema(description = "Связанный заказ официанта")
    private WaiterOrder order;
}

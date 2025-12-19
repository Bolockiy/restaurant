package liga.restaurant.kitchen.entity;

import jakarta.validation.constraints.NotNull;
import liga.restaurant.dto.OrderStatus;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class KitchenOrder {

    private Long kitchenOrderId;

    @NotNull(message = "waiterOrderNo must not be null")
    private Long waiterOrderNo;

    @NotNull(message = "status must not be null")
    private OrderStatus status;

    @NotNull(message = "createDttm must not be null")
    private OffsetDateTime createDttm;
}

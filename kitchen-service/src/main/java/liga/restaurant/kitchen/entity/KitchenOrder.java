package liga.restaurant.kitchen.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class KitchenOrder {

    private Long kitchenOrderId;

    @NotNull(message = "waiterOrderNo must not be null")
    private Long waiterOrderNo;

    @NotNull(message = "status must not be null")
    private String status;

    @NotNull(message = "createDttm must not be null")
    private OffsetDateTime createDttm;
}

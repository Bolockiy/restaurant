package liga.restaurant.kitchen.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderToDish {
    @NotNull(message = "kitchenOrderId must not be null")
    private Long kitchenOrderId;

    @NotNull(message = "dishId must not be null")
    private Long dishId;

    @NotNull(message = "dishesNumber must not be null")
    private Long dishesNumber;
}

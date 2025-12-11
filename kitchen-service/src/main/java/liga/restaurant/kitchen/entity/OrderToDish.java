package liga.restaurant.kitchen.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Schema(description = "Связь заказа кухни с блюдом")
public class OrderToDish {
    @Schema(
            description = "ID заказа кухни",
            example = "10",
            required = true
    )
    private Long kitchenOrderId;

    @NotNull(message = "dishId must not be null")
    @Schema(
            description = "ID блюда",
            example = "5",
            required = true
    )
    private Long dishId;

    @NotNull(message = "dishesNumber must not be null")
    @Positive(message = "dishesNumber must be positive")
    @Schema(
            description = "Количество порций блюда в заказе",
            example = "3",
            required = true
    )
    private Long dishesNumber;
}

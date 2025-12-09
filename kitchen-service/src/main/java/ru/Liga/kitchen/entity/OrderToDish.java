package ru.Liga.kitchen.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Связь заказа кухни с блюдом")
public class OrderToDish {

    @Schema(
            description = "ID заказа кухни",
            example = "10"
    )
    private Long kitchenOrderId;

    @Schema(
            description = "ID блюда",
            example = "5"
    )
    private Long dishId;

    @Schema(
            description = "Количество порций блюда в заказе",
            example = "3"
    )
    private Long dishesNumber;
}

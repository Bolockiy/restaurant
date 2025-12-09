package ru.Liga.kitchen.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Блюдо кухни")
public class Dish {

    @Schema(description = "ID блюда", example = "1")
    private Long dishId;

    @Schema(description = "Название блюда", example = "Пицца Маргарита")
    private String name;

    @Schema(description = "Цена блюда", example = "550")
    private Integer price;

    @Schema(description = "Остаток на складе", example = "20")
    private Integer balance;
}
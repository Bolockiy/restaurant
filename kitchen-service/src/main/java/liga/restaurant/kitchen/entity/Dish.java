package liga.restaurant.kitchen.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Блюдо кухни")
public class Dish {
    @Schema(description = "ID блюда", example = "1")
    private Long dishId;

    @NotNull(message = "name must not be null")
    @Schema(description = "Название блюда", example = "Пицца Маргарита")
    private String name;

    @NotNull(message = "price must not be null")
    @Schema(description = "Цена блюда", example = "550")
    private Integer price;

    @NotNull(message = "balance must not be null")
    @Schema(description = "Остаток на складе", example = "20")
    private Integer balance;
}
package liga.restaurant.kitchen.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Dish {
    private Long dishId;

    @NotNull(message = "name must not be null")
    private String name;

    @NotNull(message = "composition must not be null")
    private String dishComposition;

    @NotNull(message = "balance must not be null")
    private Integer balance;
}
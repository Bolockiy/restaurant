package liga.restaurant.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO для создания нового заказа официантом.
 * <p>
 * Используется для передачи данных из слоя контроллеров
 * в сервисный слой при создании заказа.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateWaiterOrderDto {

    /**
     * Номер стола, для которого создаётся заказ.
     */
    @NotNull
    private String tableNo;

    /**
     * Список блюд, входящих в заказ.
     */
    @NotNull
    @Valid
    private List<OrderToDishDto> dishes;

    /**
     * Идентификатор официанта, создающего заказ.
     */
    @NotNull
    private Long waiterId;
}

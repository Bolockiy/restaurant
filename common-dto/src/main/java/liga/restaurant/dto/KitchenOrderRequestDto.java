package liga.restaurant.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO передаёт заказ от waiter-сервиса в kitchen-сервис.
 * <p>
 * Используется как сообщение Kafka в топике waiter-to-kitchen.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KitchenOrderRequestDto {
    /**
     * waiterOrderNo – идентификатор заказа у официанта.
     */
    @NotNull
    private Long waiterOrderNo;

    /**
     * dishes – список блюд с количеством, которые необходимо приготовить.
     */
    @NotNull
    @Valid
    private List<OrderToDishDto> dishes;
}
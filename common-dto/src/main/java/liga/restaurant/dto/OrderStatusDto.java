package liga.restaurant.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для статусов о готовоности заказа
 * <p>
 * Используется как сообщение Kafka в топике kitchen-to-waiter.
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusDto {
    /**
     * waiterOrderNo – идентификатор заказа у официанта.
     */
    @NotNull
    private Long waiterOrderNo;
    /**
     * status – статус заказа (ready/failed).
     */
    @NotNull
    private OrderStatus status;
}
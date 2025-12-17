package liga.restaurant.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * DTO для статусов о готовоности заказа
 * Используется как сообщение Kafka в топике `kitchen-to-waiter`.
 *
 * waiterOrderNo – идентификатор заказа у официанта.
 * status – статус заказа (ready/failed).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusDto {
    @NotNull
    private Long waiterOrderNo;
    @NotNull
    private String status;
}
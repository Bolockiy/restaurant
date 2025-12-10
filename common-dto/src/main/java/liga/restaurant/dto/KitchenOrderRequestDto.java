package liga.restaurant.dto;

import lombok.*;
import java.util.List;

/**
 * DTO передаёт заказ от waiter-сервиса в kitchen-сервис.
 * Используется как сообщение Kafka в топике `waiter-to-kitchen`.
 *
 * waiterOrderNo – идентификатор заказа у официанта.
 * dishes – список блюд с количеством, которые необходимо приготовить.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KitchenOrderRequestDto {
    private Long waiterOrderNo;
    private List<OrderToDishDto> dishes;
}
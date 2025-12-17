package liga.restaurant.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.validation.constraints.NotNull;

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
    @NotNull
    private Long waiterOrderNo;

    @NotNull
    @Valid
    private List<OrderToDishDto> dishes;
}
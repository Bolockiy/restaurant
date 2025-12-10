package liga.restaurant.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * DTO описывает одну позицию заказа.
 * Используется внутри KitchenOrderRequestDto при отправке заказа на кухню.
 *
 * dishId — идентификатор блюда.
 * dishesNumber — количество единиц этого блюда в заказе.
 */
public class OrderToDishDto {
    private Long dishId;
    private Long dishesNumber;
}
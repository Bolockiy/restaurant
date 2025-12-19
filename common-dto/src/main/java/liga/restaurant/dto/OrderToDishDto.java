package liga.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO, описывающий одну позицию заказа.
 * <p>
 * Используется в составе KitchenOrderRequestDto
 * при отправке заказа на кухню.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderToDishDto {

    /**
     * Идентификатор блюда.
     */
    private Long dishId;

    /**
     * Количество единиц данного блюда в заказе.
     */
    private Long dishesNumber;
}
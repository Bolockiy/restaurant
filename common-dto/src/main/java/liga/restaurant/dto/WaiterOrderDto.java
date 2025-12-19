package liga.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * DTO, представляющий заказ, созданный официантом.
 * <p>
 * Используется для передачи данных между сервисом официанта (waiter-service)
 * и слоем хранения данных (базой данных).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaiterOrderDto {

    /**
     * Уникальный идентификатор заказа.
     */
    Long id;

    /**
     * Текущий статус заказа.
     */
    OrderStatus status;

    /**
     * Номер стола, к которому привязан заказ.
     */
    String tableNo;

    /**
     * Дата и время создания заказа.
     */
    OffsetDateTime createDttm;
}

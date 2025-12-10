package liga.restaurant.dto;

import lombok.*;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * DTO представляет заказ, созданный официантом.
 * Используется для передачи данных между waiter-сервисом и бд
 *
 * id — уникальный идентификатор заказа.
 * status — текущий статус заказа (NEW, COOKING, READY, FAILED и т.п.).
 * tableNo — номер стола, к которому привязан заказ.
 * createDttm — дата и время создания заказа.
 */
public class WaiterOrderDto {
    Long id;
    String status;
    String tableNo;
    OffsetDateTime createDttm;
}

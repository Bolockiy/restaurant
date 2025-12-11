package liga.restaurant.kitchen.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
@Schema(description = "Заказ кухни")
public class KitchenOrder {

    @Schema(
            description = "ID заказа в системе кухни",
            example = "10"
    )
    private Long kitchenOrderId;

    @NotNull(message = "waiterOrderNo must not be null")
    @Schema(
            description = "Номер заказа официанта",
            example = "101"
    )
    private Long waiterOrderNo;

    @NotNull(message = "status must not be null")
    @Schema(
            description = "Статус заказа",
            example = "CREATED | IN_PROGRESS | READY | FAILED"
    )
    private String status;

    @NotNull(message = "createDttm must not be null")
    @Schema(
            description = "Дата и время создания заказа",
            example = "2025-12-08T10:15:30+03:00"
    )
    private OffsetDateTime createDttm;
}

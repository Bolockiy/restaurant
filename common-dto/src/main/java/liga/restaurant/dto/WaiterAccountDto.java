package liga.restaurant.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * DTO для представления аккаунта официанта.
 * <p>
 * Используется для передачи данных между слоем контроллеров
 * и сервисным слоем приложения.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaiterAccountDto {

    /**
     * Уникальный идентификатор официанта в базе данных.
     */
    private Long id;

    /**
     * Имя официанта.
     * <p>
     * Поле обязательно для заполнения.
     */
    @NotNull
    private String name;

    /**
     * Пол официанта.
     */
    private String sex;

    /**
     * Дата и время трудоустройства официанта.
     */
    private OffsetDateTime employmentDate;
}


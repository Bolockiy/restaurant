package liga.restaurant.dto;
import lombok.*;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * DTO для представления аккаунта официанта.
 * Используется для передачи данных между слоем контроллеров и сервисным слоем.
 *
 * id             — уникальный идентификатор официанта в базе данных.
 * name           — имя официанта.
 * sex            — пол официанта.
 * employmentDate — дата трудоустройства официанта.
 */
public class WaiterAccountDto {
    private Long id;
    private String name;
    private String sex;
    private OffsetDateTime employmentDate;
}

package liga.restaurant.dto;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateWaiterOrderDto {

    @NotNull
    private String tableNo;

    @NotNull
    private List<OrderToDishDto> dishes;

    @NotNull
    private Long waiterId;
}

package liga.restaurant.waiter.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_positions", schema = "waiter")
@Getter
@Setter
@Schema(description = "Позиция заказа официанта (конкретное блюдо в заказе)")
public class OrderPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "composition_id_seq")
    @SequenceGenerator(
            name = "composition_id_seq",
            sequenceName = "waiter.order_positions_composition_id_seq",
            allocationSize = 1
    )
    @Column(name = "composition_id")
    @Schema(description = "Уникальный идентификатор позиции заказа", example = "10")
    private Long id;

    @Column(name = "dish_num", nullable = false)
    @Schema(description = "Количество выбранных блюд", example = "3", required = true)
    private Long dishNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_no", nullable = false)
    @JsonBackReference
    @Schema(description = "Заказ официанта, к которому относится позиция")
    private WaiterOrder order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_position_id", nullable = false)
    @Schema(description = "Позиция меню")
    private Menu menu;
}

package liga.restaurant.waiter.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "menu")
@Getter
@Setter
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_id_seq")
    @SequenceGenerator(
            name = "menu_id_seq",
            sequenceName = "waiter.menu_id_seq",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "dish_name", nullable = false)
    private String dishName;

    @Column(name = "dish_cost", nullable = false)
    private BigDecimal dishCost;
}

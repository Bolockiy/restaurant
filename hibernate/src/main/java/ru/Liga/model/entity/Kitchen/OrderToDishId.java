package ru.Liga.model.entity.Kitchen;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
public class OrderToDishId implements Serializable {
    private Long kitchenOrderId;
    private Long dishId;
}

package ru.Liga.dto;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KitchenDto {
    private Long orderId;
    private String action;
}

package ru.Liga.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderToDishDto {
    private Long dishId;
    private Long dishesNumber;
}
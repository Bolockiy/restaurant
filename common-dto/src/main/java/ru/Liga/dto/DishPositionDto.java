package ru.Liga.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishPositionDto {
    private Long dishId;
    private Long dishesNumber;
}
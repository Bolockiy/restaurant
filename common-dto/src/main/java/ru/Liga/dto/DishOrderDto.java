package ru.Liga.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DishOrderDto {
    private Long dishId;
    private Long quantity;
}
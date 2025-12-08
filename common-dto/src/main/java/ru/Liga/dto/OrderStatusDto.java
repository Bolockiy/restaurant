package ru.Liga.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class OrderStatusDto {
    private Long waiterOrderNo;
    private String status;
    public OrderStatusDto(){}
}
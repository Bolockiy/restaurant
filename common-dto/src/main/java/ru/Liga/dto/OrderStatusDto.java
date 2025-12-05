package ru.Liga.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusDto {
    private Long waiterOrderNo;
    private String status; // READY
}
package ru.Liga.kitchen.entity;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class KitchenOrder {
    private Long kitchenOrderId;
    private Long waiterOrderNo;
    private String status;
    private OffsetDateTime createDttm;
}

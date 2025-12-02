package ru.Liga.kitchen.entity;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class KitchenOrder {
    private Long kitchen_order_id;
    private Long waiter_order_no;
    private String status;
    private OffsetDateTime create_dttm;
}

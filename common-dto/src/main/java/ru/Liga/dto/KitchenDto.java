package ru.Liga.dto;

public class KitchenDto {
    private Long orderId;
    private String action;

    public KitchenDto() {
    }

    public KitchenDto(Long orderId, String action) {
        this.orderId = orderId;
        this.action = action;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}

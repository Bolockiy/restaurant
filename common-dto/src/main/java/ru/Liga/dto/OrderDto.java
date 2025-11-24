package ru.Liga.dto;

public class OrderDto {
    private Long id;
    private String dish;
    private String status;

    public OrderDto() {
    }

    public OrderDto(Long id, String dish, String status) {
        this.id = id;
        this.dish = dish;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

package ru.Liga.dto;

import lombok.Getter;
import lombok.Setter;

public class WaiterAccountDto {
    @Setter
    @Getter
    private Long id;
    private Long order_no;
    private String name;
    private String sex;
    private String employment_date;
    public WaiterAccountDto(Long id, Long order_no,String name, String sex, String employment_date) {
        this.id = id;
        this.order_no = order_no;
        this.name = name;
        this.sex = sex;
        this.employment_date = employment_date;
    }
}

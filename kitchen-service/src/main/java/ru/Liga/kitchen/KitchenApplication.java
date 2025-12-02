package ru.Liga.kitchen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("ru.Liga.kitchen.mapper")
public class KitchenApplication {
    public static void main(String[] args) {
        SpringApplication.run(KitchenApplication.class, args);
    }
}

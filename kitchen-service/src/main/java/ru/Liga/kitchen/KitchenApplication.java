package ru.Liga.kitchen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@SpringBootApplication
@MapperScan("ru.Liga.kitchen.mapper")
@EnableFeignClients
public class KitchenApplication {
    public static void main(String[] args) {
        SpringApplication.run(KitchenApplication.class, args);
    }
}

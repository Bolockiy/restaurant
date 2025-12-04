package ru.Liga.kitchen.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.Liga.dto.OrderStatusDto;

@FeignClient(name = "waiter-service", url = "http://localhost:8083") // адрес сервиса официанта
public interface WaiterFeignClient {

    @PostMapping("/orders/status")
    void updateOrderStatus(@RequestBody OrderStatusDto statusDto);
}

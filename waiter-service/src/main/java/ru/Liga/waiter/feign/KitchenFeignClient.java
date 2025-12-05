package ru.Liga.waiter.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.Liga.dto.KitchenOrderRequestDto;

@FeignClient(name = "kitchen-service", url = "http://localhost:8082/")
public interface KitchenFeignClient {
    @PostMapping("/kitchen/orders/internal")
    void sendOrderToKitchen(@RequestBody KitchenOrderRequestDto dto);
}

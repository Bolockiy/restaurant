package ru.Liga.waiter.kafka;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.Liga.dto.OrderStatusDto;
import ru.Liga.waiter.service.WaiterOrderService;

@Service
public class KitchenStatusConsumer {

    private final WaiterOrderService waiterOrderService;

    public KitchenStatusConsumer(WaiterOrderService waiterOrderService) {
        this.waiterOrderService = waiterOrderService;
    }

    @KafkaListener(
            topics = "kitchen-to-waiter",
            groupId = "waiter-group",
            containerFactory = "statusKafkaListenerContainerFactory"
    )
    public void consumeStatus(OrderStatusDto dto) {
        System.out.println("Get status to waiter: " + dto.getWaiterOrderNo() + " -> " + dto.getStatus());
        waiterOrderService.updateOrderStatus(dto.getWaiterOrderNo(), dto.getStatus());
    }
}
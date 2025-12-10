package liga.restaurant.waiter.kafka;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import liga.restaurant.dto.OrderStatusDto;
import liga.restaurant.waiter.service.WaiterOrderService;

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
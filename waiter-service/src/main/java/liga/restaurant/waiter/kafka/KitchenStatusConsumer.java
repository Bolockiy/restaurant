package liga.restaurant.waiter.kafka;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import liga.restaurant.dto.OrderStatusDto;
import liga.restaurant.waiter.service.WaiterOrderService;

@Service
@Slf4j
@RequiredArgsConstructor
public class KitchenStatusConsumer {
    private final WaiterOrderService waiterOrderService;

    @KafkaListener(
            topics = "${app.kafka.topics.kitchen-to-waiter}",
            groupId = "${app.kafka.groups.kitchen}"
    )
    public void consumeStatus(OrderStatusDto dto) {
        log.info("Received order status: orderNo={} status={}", dto.getWaiterOrderNo(), dto.getStatus());
        waiterOrderService.updateOrderStatus(dto.getWaiterOrderNo(), dto.getStatus());
    }
}

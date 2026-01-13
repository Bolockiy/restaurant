package liga.restaurant.waiter.kafka;


import liga.restaurant.dto.OrderStatusDto;
import liga.restaurant.waiter.service.WaiterOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Kafka consumer waiter
 * Используется для принятия и обновления статуса блюда от кухни
 * из kitchen-сервиса в waiter-сервис.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class WaiterStatusConsumer {
    private final WaiterOrderService waiterOrderService;

    @KafkaListener(
            topics = "${app.kafka.topics.kitchen-to-waiter}",
            groupId = "${app.kafka.groups.kitchen}"
    )
    public void consumeStatus(OrderStatusDto dto) {
        log.info(
                "Получен статус заказа: orderNo={}, status={}",
                dto.getWaiterOrderNo(),
                dto.getStatus()
        );
        waiterOrderService.updateOrderStatus(dto.getWaiterOrderNo(), dto.getStatus());
    }
}

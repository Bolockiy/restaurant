package liga.restaurant.kitchen.Kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import liga.restaurant.dto.KitchenOrderRequestDto;
import liga.restaurant.dto.OrderStatusDto;
import liga.restaurant.kitchen.service.KitchenService;

@Service
@RequiredArgsConstructor
@Slf4j
public class KitchenKafkaConsumer {
    private final KitchenService kitchenService;
    private final KitchenKafkaProducer kitchenKafkaProducer;
    @KafkaListener(
            topics = "${app.kafka.topics.waiter-to-kitchen}",
            groupId = "${app.kafka.groups.kitchen}"
    )
    public void consume(KitchenOrderRequestDto dto) {
        log.info("Received order from waiter: orderNo={} with {} dishes",
                dto.getWaiterOrderNo(),
                dto.getDishes() != null ? dto.getDishes().size() : 0);
        if (kitchenService.processOrderFromWaiter(dto))
            kitchenKafkaProducer.sendStatusToWaiter(new OrderStatusDto(dto.getWaiterOrderNo(), "READY"));
        else
            kitchenKafkaProducer.sendStatusToWaiter(new OrderStatusDto(dto.getWaiterOrderNo(), "FAILED"));
    }
}
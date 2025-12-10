package liga.restaurant.kitchen.Kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import liga.restaurant.dto.KitchenOrderRequestDto;
import liga.restaurant.dto.OrderStatusDto;
import liga.restaurant.kitchen.service.KitchenService;

@Service
@RequiredArgsConstructor
public class KitchenKafkaConsumer {

    private final KitchenService kitchenService;
    private final KitchenKafkaProducer kitchenKafkaProducer;
    @KafkaListener(
            topics = "waiter-to-kitchen",
            groupId = "kitchen-group"
    )
    public void consume(KitchenOrderRequestDto dto) {
        if (kitchenService.processOrderFromWaiter(dto))
            kitchenKafkaProducer.sendStatusToWaiter(new OrderStatusDto(dto.getWaiterOrderNo(), "READY"));
        else
            kitchenKafkaProducer.sendStatusToWaiter(new OrderStatusDto(dto.getWaiterOrderNo(), "FAILED"));
    }
}
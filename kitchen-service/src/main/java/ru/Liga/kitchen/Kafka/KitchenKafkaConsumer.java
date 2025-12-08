package ru.Liga.kitchen.Kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.Liga.dto.KitchenOrderRequestDto;
import ru.Liga.dto.OrderStatusDto;
import ru.Liga.kitchen.service.KitchenService;

@Service
public class KitchenKafkaConsumer {

    private final KitchenService kitchenService;

    public KitchenKafkaConsumer(KitchenService kitchenService) {
        this.kitchenService = kitchenService;
    }

    @KafkaListener(
            topics = "waiter-to-kitchen",
            groupId = "kitchen-group"
    )
    public void consume(KitchenOrderRequestDto dto) {
        System.out.println("Received order in kitchen: " + dto);
        boolean productsEnough = kitchenService.checkProductsAvailability(dto);

        if (productsEnough) {
            kitchenService.processOrderFromWaiter(dto);
            kitchenService.getKitchenKafkaProducer().sendStatusToWaiter(
                    new OrderStatusDto(dto.getWaiterOrderNo(), "READY")
            );
        } else {
            kitchenService.getKitchenKafkaProducer().sendStatusToWaiter(
                    new OrderStatusDto(dto.getWaiterOrderNo(), "FAILED")
            );
        }
    }
}
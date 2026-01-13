package liga.restaurant.kitchen.Kafka;

import liga.restaurant.dto.KitchenOrderRequestDto;
import liga.restaurant.dto.OrderStatus;
import liga.restaurant.dto.OrderStatusDto;
import liga.restaurant.kitchen.service.KitchenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Kafka consumer кухни.
 * Получает заказы от waiter-сервиса из Kafka,
 * инициирует их обработку и отправляет статус обратно официанту.
 */
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
        log.info(
                "Получен заказ от официанта: orderNo={}, количество блюд={}",
                dto.getWaiterOrderNo(),
                dto.getDishes() != null ? dto.getDishes().size() : 0
        );
        if (kitchenService.processOrderFromWaiter(dto)) {
            kitchenKafkaProducer.sendStatusToWaiter(
                    new OrderStatusDto(dto.getWaiterOrderNo(), OrderStatus.COOKING)
            );
        } else {
            kitchenKafkaProducer.sendStatusToWaiter(
                    new OrderStatusDto(dto.getWaiterOrderNo(), OrderStatus.FAILED)
            );
        }
    }
}
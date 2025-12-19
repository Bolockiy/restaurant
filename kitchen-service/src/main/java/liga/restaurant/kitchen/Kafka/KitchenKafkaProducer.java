package liga.restaurant.kitchen.Kafka;

import liga.restaurant.dto.OrderStatusDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Kafka producer кухни.
 * Используется для отправки статусов заказов
 * из kitchen-сервиса в waiter-сервис.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class KitchenKafkaProducer {
    private final KafkaTemplate<String, OrderStatusDto> kafkaTemplate;

    @Value("${app.kafka.topics.kitchen-to-waiter}")
    private String topic;

    public void sendStatusToWaiter(OrderStatusDto dto) {
        log.info("Sending status to waiter: orderNo={} -> status={}", dto.getWaiterOrderNo(), dto.getStatus());
        kafkaTemplate.send(topic, dto);
    }
}

package liga.restaurant.waiter.kafka;

import liga.restaurant.dto.KitchenOrderRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Kafka producer waiter
 * Используется для отправки заказов
 * из waiter-сервиса в kitchen-сервис.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class WaiterKafkaProducer {
    private final KafkaTemplate<String, KitchenOrderRequestDto> kafkaTemplate;
    @Value("${app.kafka.topics.waiter-to-kitchen}")
    private String topic;

    public void sendOrderToKitchen(KitchenOrderRequestDto dto) {
        log.info("Отправка заказа на кухню: {}", dto);
        kafkaTemplate.send(topic, dto);
    }
}
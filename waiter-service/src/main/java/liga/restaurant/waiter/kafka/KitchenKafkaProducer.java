package liga.restaurant.waiter.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import liga.restaurant.dto.KitchenOrderRequestDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class KitchenKafkaProducer {
    private final KafkaTemplate<String, KitchenOrderRequestDto> kafkaTemplate;
    @Value("${app.kafka.topics.waiter-to-kitchen}")
    private String topic;

    public void sendOrderToKitchen(KitchenOrderRequestDto dto) {
        log.info("Sending order to kitchen: {}", dto);
        try {
            kafkaTemplate.send(topic, dto);
        } catch (Exception e) {
            log.error("Failed to send order to kitchen: {}", dto, e);
            e.printStackTrace();
        }
    }
}
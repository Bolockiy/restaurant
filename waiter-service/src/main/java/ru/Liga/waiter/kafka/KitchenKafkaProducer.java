package ru.Liga.waiter.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.Liga.dto.KitchenOrderRequestDto;

@Service
public class KitchenKafkaProducer {

    private final KafkaTemplate<String, KitchenOrderRequestDto> kafkaTemplate;
    private final String topic = "waiter-to-kitchen";

    public KitchenKafkaProducer(KafkaTemplate<String, KitchenOrderRequestDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderToKitchen(KitchenOrderRequestDto dto) {
        System.out.println("Sending order to kitchen: " + dto);
        try {
            kafkaTemplate.send(topic, dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package ru.Liga.kitchen.Kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.Liga.dto.OrderStatusDto;

@Service
public class KitchenKafkaProducer {

    private final KafkaTemplate<String, OrderStatusDto> kafkaTemplate;
    private final String topic = "kitchen-to-waiter";

    public KitchenKafkaProducer(KafkaTemplate<String, OrderStatusDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendStatusToWaiter(OrderStatusDto dto) {
        System.out.println("Sending status to waiter: " + dto.getWaiterOrderNo() + " -> " + dto.getStatus());
        kafkaTemplate.send(topic, dto);
    }
}
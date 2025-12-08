package ru.Liga.waiter.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.Liga.dto.KitchenOrderRequestDto;
import ru.Liga.dto.WaiterOrderDto;
import ru.Liga.waiter.entity.WaiterOrder;
import ru.Liga.waiter.kafka.KitchenKafkaProducer;
import ru.Liga.waiter.repository.WaiterOrderRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WaiterOrderService {

    private final WaiterOrderRepository repo;
    private final KitchenKafkaProducer kafkaProducer;

    public WaiterOrderService(WaiterOrderRepository repo,
                              KitchenKafkaProducer kafkaProducer) {
        this.repo = repo;
        this.kafkaProducer = kafkaProducer;
    }

    public WaiterOrder createOrder(WaiterOrder dto) {
        return repo.save(dto);
    }

    public void createOrderKitchen(KitchenOrderRequestDto dto) {
        kafkaProducer.sendOrderToKitchen(dto);
    }

    public List<WaiterOrderDto> findAll() {
        return repo.findAll().stream()
                .map(o -> new WaiterOrderDto(
                        o.getId(),
                        o.getStatus(),
                        o.getTableNo(),
                        o.getCreateDttm()
                ))
                .collect(Collectors.toList());
    }

    public WaiterOrderDto findById(Long id) {
        return repo.findById(id)
                .map(o -> new WaiterOrderDto(
                        o.getId(),
                        o.getStatus(),
                        o.getTableNo(),
                        o.getCreateDttm()
                ))
                .orElse(null);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Transactional
    public void updateOrderStatus(Long waiterOrderNo, String status) {
        WaiterOrder order = repo.findById(waiterOrderNo)
                .orElseThrow(() -> new RuntimeException("Order not found: " + waiterOrderNo));

        if (status.equals(order.getStatus())) {
            return; // ничего не делаем, чтобы не вызвать лишние события
        }

        order.setStatus(status);
        repo.save(order);
    }
}

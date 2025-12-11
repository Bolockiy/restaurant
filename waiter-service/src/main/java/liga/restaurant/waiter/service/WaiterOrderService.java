package liga.restaurant.waiter.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import liga.restaurant.dto.KitchenOrderRequestDto;
import liga.restaurant.dto.WaiterOrderDto;
import liga.restaurant.waiter.entity.WaiterOrder;
import liga.restaurant.waiter.kafka.KitchenKafkaProducer;
import liga.restaurant.waiter.repository.WaiterOrderRepository;
import ru.Liga.restaurant.BusinessException;
import ru.Liga.restaurant.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WaiterOrderService {
    private final WaiterOrderRepository repo;
    private final KitchenKafkaProducer kafkaProducer;

    public WaiterOrder createOrder(WaiterOrder dto) {
        log.info("Создание нового заказа: tableNo={}, status={}", dto.getTableNo(), dto.getStatus());
        WaiterOrder saved = repo.save(dto);
        log.debug("Заказ сохранён: id={}", saved.getId());
        return saved;
    }

    public void createOrderKitchen(KitchenOrderRequestDto dto) {
        log.info("Отправка заказа на кухню: waiterOrderNo={}", dto.getWaiterOrderNo());
        kafkaProducer.sendOrderToKitchen(dto);
        log.debug("Заказ отправлен в Kafka: {}", dto);
    }

    public List<WaiterOrderDto> findAll() {
        log.debug("Получение всех заказов");
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
        log.debug("Получение заказа по id={}", id);
        return repo.findById(id)
                .map(o -> {
                    log.info("Заказ найден: id={}", o.getId());
                    return new WaiterOrderDto(
                            o.getId(),
                            o.getStatus(),
                            o.getTableNo(),
                            o.getCreateDttm()
                    );
                })
                .orElseThrow(() -> {
                    log.warn("Заказ не найден: id={}", id);
                    return new NotFoundException("Order not found with id " + id);
                });
    }

    public void delete(Long id) {
        log.info("Удаление заказа: id={}", id);
        repo.deleteById(id);
    }

    public void updateOrderStatus(Long waiterOrderNo, String status) {
        log.info("Обновление статуса заказа: id={}, newStatus={}", waiterOrderNo, status);
        WaiterOrder order = repo.findById(waiterOrderNo)
                .orElseThrow(() -> {
                    log.warn("Заказ не найден для обновления статуса: id={}", waiterOrderNo);
                    return new NotFoundException("Order not found: " + waiterOrderNo);
                });

        if (status.equals(order.getStatus())) {
            log.debug("Статус заказа не изменился: id={}, status={}", waiterOrderNo, status);
            return;
        }

        order.setStatus(status);
        repo.save(order);
        log.info("Статус заказа обновлён: id={}, newStatus={}", waiterOrderNo, status);
    }
}

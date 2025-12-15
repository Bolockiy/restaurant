package liga.restaurant.waiter.service;

import jakarta.transaction.Transactional;
import liga.restaurant.dto.CreateWaiterOrderDto;
import liga.restaurant.dto.WaiterAccountDto;
import liga.restaurant.waiter.entity.Menu;
import liga.restaurant.waiter.entity.OrderPosition;
import liga.restaurant.waiter.entity.WaiterAccount;
import liga.restaurant.waiter.repository.WaiterAccountRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WaiterOrderService {
    private final WaiterOrderRepository repo;
    private final KitchenKafkaProducer kafkaProducer;
    private final WaiterAccountRepository waiterAccountRepository;


    @Transactional
    public WaiterOrder createOrderKitchen(CreateWaiterOrderDto dto) {
        log.info("Создание заказа официантом, tableNo={}", dto.getTableNo());
        if (dto.getWaiterId() == null) {
            throw new IllegalArgumentException("Waiter ID cannot be null");
        }

        WaiterAccount waiter = waiterAccountRepository.findById(dto.getWaiterId())
                .orElseThrow(() -> new RuntimeException("Waiter not found: " + dto.getWaiterId()));

        WaiterOrder order = new WaiterOrder();
        order.setTableNo(dto.getTableNo());
        order.setStatus("CREATED");
        order.setCreateDttm(OffsetDateTime.now());
        order.setWaiter(waiter);
        WaiterOrder savedOrder = repo.save(order);

        log.info("Заказ сохранён в waiter DB, id={}", savedOrder.getId());

        // 4. Kafka DTO для кухни
        KitchenOrderRequestDto kitchenDto =
                new KitchenOrderRequestDto(
                        savedOrder.getId(),
                        dto.getDishes()
                );

        // 5. Отправляем в Kafka
        kafkaProducer.sendOrderToKitchen(kitchenDto);

        log.info("Заказ {} отправлен на кухню", savedOrder.getId());

        return savedOrder;
    }

    public Page<WaiterOrderDto> findAll(Pageable pageable) {
        log.debug("Получение всех заказов с пагинацией: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());

        return repo.findAll(pageable)
                .map(o -> new WaiterOrderDto(
                        o.getId(),
                        o.getStatus(),
                        o.getTableNo(),
                        o.getCreateDttm()
                ));
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

package liga.restaurant.waiter.service;

import jakarta.transaction.Transactional;
import liga.restaurant.dto.CreateWaiterOrderDto;
import liga.restaurant.waiter.entity.WaiterAccount;
import liga.restaurant.waiter.repository.WaiterAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import liga.restaurant.dto.KitchenOrderRequestDto;
import liga.restaurant.dto.WaiterOrderDto;
import liga.restaurant.waiter.entity.WaiterOrder;
import liga.restaurant.waiter.kafka.WaiterKafkaProducer;
import liga.restaurant.waiter.repository.WaiterOrderRepository;
import liga.restaurant.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class WaiterOrderService {
    /**
     * Создаёт заказ официанта и отправляет его на кухню.
     *
     * @param dto DTO создания заказа
     * @return сохранённый заказ официанта
     */
    @Transactional
    public WaiterOrder createOrderKitchen(CreateWaiterOrderDto dto) {
        log.info("Создание заказа официантом, tableNo={}", dto.getTableNo());
        if (dto.getWaiterId() == null) {
            throw new IllegalArgumentException("Waiter ID cannot be null");
        }

        WaiterAccount waiter = waiterAccountRepository.findById(dto.getWaiterId())
                .orElseThrow(() -> new NotFoundException("Waiter not found: " + dto.getWaiterId()));

        WaiterOrder order = new WaiterOrder();
        order.setTableNo(dto.getTableNo());
        order.setStatus("COOKING");
        order.setCreateDttm(OffsetDateTime.now());
        order.setWaiter(waiter);
        WaiterOrder savedOrder = repo.save(order);

        log.info("Заказ сохранён в waiter DB, id={}", savedOrder.getId());

        KitchenOrderRequestDto kitchenDto =
                new KitchenOrderRequestDto(
                        savedOrder.getId(),
                        dto.getDishes()
                );

        kafkaProducer.sendOrderToKitchen(kitchenDto);

        log.info("Заказ {} отправлен на кухню", savedOrder.getId());

        return savedOrder;
    }
    /**
     * Возвращает список заказов официанта с пагинацией.
     *
     * @param pageable параметры пагинации (page, size, sort)
     * @return страница заказов официанта
     */
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
    /**
     * Получает заказ официанта по идентификатору.
     *
     * @param id идентификатор заказа
     * @return DTO заказа официанта
     * @throws NotFoundException если заказ не найден
     */
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
    /**
     * Удаляет заказ официанта по идентификатору.
     *
     * @param id идентификатор заказа
     */
    public void delete(Long id) {
        log.info("Удаление заказа: id={}", id);
        repo.deleteById(id);
    }
    /**
     * Обновляет статус заказа официанта.
     * Используется при получении статусов из kitchen-сервиса.
     *
     * @param waiterOrderNo идентификатор заказа официанта
     * @param status новый статус заказа
     */
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
    private final WaiterOrderRepository repo;
    private final WaiterKafkaProducer kafkaProducer;
    private final WaiterAccountRepository waiterAccountRepository;
}

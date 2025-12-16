package liga.restaurant.kitchen.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import liga.restaurant.dto.KitchenOrderRequestDto;
import liga.restaurant.dto.OrderStatusDto;
import liga.restaurant.dto.OrderToDishDto;
import liga.restaurant.kitchen.Kafka.KitchenKafkaProducer;
import liga.restaurant.kitchen.entity.Dish;
import liga.restaurant.kitchen.entity.KitchenOrder;
import liga.restaurant.kitchen.entity.OrderToDish;
import ru.Liga.restaurant.NotFoundException;
import ru.Liga.restaurant.BusinessException;
import liga.restaurant.kitchen.mapper.KitchenOrderMapper;
import java.time.OffsetDateTime;
import java.util.List;
import liga.restaurant.kitchen.mapper.KitchenOrderMapper;
@Service
@RequiredArgsConstructor
@Slf4j
public class KitchenService {

    /**
     * Обрабатывает заказ, поступивший от waiter-сервиса.
     * Метод выполняется в одной транзакции.
     *
     * @param dto заказ от официанта (номер заказа и блюда)
     * @return true — если заказ успешно принят кухней
     *
     * @throws BusinessException если недостаточно продуктов
     * @throws NotFoundException если блюдо не найдено
     */
    @Transactional
    public boolean processOrderFromWaiter(KitchenOrderRequestDto dto) {
        log.info("Processing order from waiter: waiterOrderNo={}", dto.getWaiterOrderNo());

        for (OrderToDishDto d : dto.getDishes()) {
            dishService.checkAvailability(d.getDishId(), d.getDishesNumber());
        }
        for (OrderToDishDto d : dto.getDishes()) {
            dishService.decreaseBalance(d.getDishId(), d.getDishesNumber());
        }

        KitchenOrder order = new KitchenOrder();
        order.setWaiterOrderNo(dto.getWaiterOrderNo());
        order.setStatus("COOKING");
        order.setCreateDttm(OffsetDateTime.now());

        kitchenOrderMapper.insert(order);
        log.info("Inserted kitchen order: kitchenOrderId={}", order.getKitchenOrderId());

        Long kitchenOrderId = order.getKitchenOrderId();
        for (OrderToDishDto d : dto.getDishes()) {
            OrderToDish otd = new OrderToDish();
            otd.setKitchenOrderId(kitchenOrderId);
            otd.setDishId(d.getDishId());
            otd.setDishesNumber(d.getDishesNumber());

            orderToDishService.create(otd);

            log.debug(
                    "Created OrderToDish: kitchenOrderId={}, dishId={}, number={}",
                    kitchenOrderId,
                    d.getDishId(),
                    d.getDishesNumber()
            );
        }

        return true;
    }

    /**
     * Помечает заказ кухни как READY (готов).
     * После этого отправляет статус официанту через Kafka.
     *
     * @param kitchenOrderId идентификатор заказа кухни
     *
     * @throws BusinessException если заказ уже имеет статус READY
     * @throws NotFoundException если заказ не найден
     */
    public void markOrderReady(Long kitchenOrderId) {
        log.info("Marking order as READY: kitchenOrderId={}", kitchenOrderId);

        KitchenOrder order = kitchenOrderMapper.findById(kitchenOrderId);

        if (order == null) {
            log.warn("Kitchen order not found: kitchenOrderId={}", kitchenOrderId);
            throw new NotFoundException("Заказ кухни не найден: " + kitchenOrderId);
        }

        if ("READY".equalsIgnoreCase(order.getStatus())) {
            log.warn("Order already READY: kitchenOrderId={}", kitchenOrderId);
            throw new BusinessException("Заказ уже помечен как READY: " + kitchenOrderId);
        }

        order.setStatus("READY");
        kitchenOrderMapper.update(order);
        log.info("Order marked as READY: kitchenOrderId={}", kitchenOrderId);

        OrderStatusDto dto = new OrderStatusDto(order.getWaiterOrderNo(), "READY");
        kitchenKafkaProducer.sendStatusToWaiter(dto);

        log.debug("Sent order status to waiter: {}", dto);
    }

    /**
     * Возвращает заказ кухни по его идентификатору.
     *
     * @param id идентификатор заказа кухни
     * @return заказ кухни
     *
     * @throws NotFoundException если заказ не найден
     */
    public KitchenOrder getById(Long id) {
        log.info("Fetching kitchen order by id={}", id);

        KitchenOrder order = kitchenOrderMapper.findById(id);
        if (order == null) {
            log.warn("Kitchen order not found: id={}", id);
            throw new NotFoundException("Заказ кухни не найден: " + id);
        }

        log.debug("Found kitchen order: {}", order);
        return order;
    }

    /**
     * Возвращает список всех заказов кухни.
     *
     * @return список заказов кухни
     */
    public List<KitchenOrder> getAll(int page, int size) {
        log.info("Fetching kitchen orders: page={}, size={}", page, size);

        int offset = page * size;

        List<KitchenOrder> orders = kitchenOrderMapper.findAll(size, offset);
        log.debug("Found {} kitchen orders", orders.size());

        return orders;
    }

    /**
     * Обновляет заказ кухни.
     *
     * @param kitchenOrder заказ кухни с обновлёнными данными
     */
    public void update(KitchenOrder kitchenOrder) {
        kitchenOrderMapper.update(kitchenOrder);
        log.info("Updated kitchen order: kitchenOrderId={}", kitchenOrder.getKitchenOrderId());
    }

    /**
     * Удаляет заказ кухни и все связанные с ним блюда.
     * Выполняется в одной транзакции.
     *
     * @param id идентификатор заказа кухни
     */
    @Transactional
    public void delete(Long id) {
        log.info("Deleting kitchen order: kitchenOrderId={}", id);

        orderToDishService.deleteByOrderId(id);
        kitchenOrderMapper.delete(id);

        log.debug("Deleted kitchen order and associated dishes: kitchenOrderId={}", id);
    }

    private final KitchenOrderMapper kitchenOrderMapper;
    private final OrderToDishService orderToDishService;
    private final KitchenKafkaProducer kitchenKafkaProducer;
    private final DishService dishService;
}
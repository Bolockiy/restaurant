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
//import ru.Liga.restaurant.NotFoundException;
//import ru.Liga.restaurant.BusinessException;
import liga.restaurant.kitchen.mapper.KitchenOrderMapper;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class KitchenService {
    private final KitchenOrderMapper kitchenOrderMapper;
    private final OrderToDishService orderToDishService;
    private final KitchenKafkaProducer kitchenKafkaProducer;
    private final DishService dishService;

    public boolean checkProductsAvailability(KitchenOrderRequestDto dto) {
        log.info("Checking product availability for waiterOrderNo={}", dto.getWaiterOrderNo());
        for (OrderToDishDto dish : dto.getDishes()) {
            Dish d = dishService.getById(dish.getDishId());
            log.debug("Dish {} has balance={}, requested={}", d.getName(), d.getBalance(), dish.getDishesNumber());
            if (d.getBalance() < dish.getDishesNumber()) {
                log.warn("Insufficient balance for dish: {}. Order cannot be processed.", d.getName());
                return false;
            }
        }
        log.info("All products available for order: waiterOrderNo={}", dto.getWaiterOrderNo());
        return true;
    }

    @Transactional
    public boolean processOrderFromWaiter(KitchenOrderRequestDto dto) {
        log.info("Processing order from waiter: waiterOrderNo={}", dto.getWaiterOrderNo());
        if (!checkProductsAvailability(dto)) {
            log.warn("Order cannot be processed due to insufficient products: waiterOrderNo={}", dto.getWaiterOrderNo());
            return false;
        }

        KitchenOrder order = new KitchenOrder();
        order.setWaiterOrderNo(dto.getWaiterOrderNo());
        order.setStatus("RECEIVED");
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
            log.debug("Created OrderToDish: kitchenOrderId={}, dishId={}, number={}",
                    kitchenOrderId, d.getDishId(), d.getDishesNumber());
        }
        return true;
    }

    public void markOrderReady(Long kitchenOrderId) {
        log.info("Marking order as READY: kitchenOrderId={}", kitchenOrderId);
        KitchenOrder order = kitchenOrderMapper.findById(kitchenOrderId);
        if ("READY".equalsIgnoreCase(order.getStatus())) {
            log.warn("Order already READY: kitchenOrderId={}", kitchenOrderId);
            //throw new BusinessException("Order is already READY");
        }
        order.setStatus("READY");
        kitchenOrderMapper.update(order);
        log.info("Order marked as READY: kitchenOrderId={}", kitchenOrderId);

        OrderStatusDto dto = new OrderStatusDto(order.getWaiterOrderNo(), "READY");
        kitchenKafkaProducer.sendStatusToWaiter(dto);
        log.debug("Sent order status to waiter: {}", dto);
    }

    public KitchenOrder getById(Long id) {
        log.info("Fetching kitchen order by id={}", id);
        KitchenOrder order = kitchenOrderMapper.findById(id);
        if (order == null) log.warn("Kitchen order not found: id={}", id);
        else log.debug("Found kitchen order: {}", order);
        return order;
    }

    public List<KitchenOrder> getAll() {
        log.info("Fetching all kitchen orders");
        List<KitchenOrder> orders = kitchenOrderMapper.findAll();
        log.debug("Found {} kitchen orders", orders.size());
        return orders;
    }

    public void create(KitchenOrder kitchenOrder) {
        kitchenOrder.setStatus("NEW");
        kitchenOrder.setCreateDttm(OffsetDateTime.now());
        kitchenOrderMapper.insert(kitchenOrder);
        log.info("Created new kitchen order: kitchenOrderId={}", kitchenOrder.getKitchenOrderId());
    }

    public void update(KitchenOrder kitchenOrder) {
        kitchenOrderMapper.update(kitchenOrder);
        log.info("Updated kitchen order: kitchenOrderId={}", kitchenOrder.getKitchenOrderId());
    }

    @Transactional
    public void delete(Long id) {
        log.info("Deleting kitchen order: kitchenOrderId={}", id);
        orderToDishService.deleteByOrderId(id);
        kitchenOrderMapper.delete(id);
        log.debug("Deleted kitchen order and associated dishes: kitchenOrderId={}", id);
    }
}
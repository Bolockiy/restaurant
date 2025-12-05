package ru.Liga.kitchen.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.Liga.dto.KitchenOrderRequestDto;
import ru.Liga.dto.OrderStatusDto;
import ru.Liga.dto.OrderToDishDto;
import ru.Liga.kitchen.entity.KitchenOrder;
import ru.Liga.kitchen.entity.OrderToDish;
import ru.Liga.kitchen.feign.WaiterFeignClient;
import ru.Liga.kitchen.mapper.KitchenOrderMapper;
import ru.Liga.kitchen.mapper.OrderToDishMapper;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class KitchenService {

    private final KitchenOrderMapper kitchenOrderMapper;
    private final WaiterFeignClient waiterClient;
    private final OrderToDishService orderToDishService;
    public KitchenService(KitchenOrderMapper kitchenOrderMapper, WaiterFeignClient waiterClient, OrderToDishService orderToDishService) {
        this.kitchenOrderMapper = kitchenOrderMapper;
        this.waiterClient = waiterClient;
        this.orderToDishService = orderToDishService;
    }
    public void setOrderReady(KitchenOrderRequestDto dto) {
        KitchenOrder order = new KitchenOrder();
        order.setWaiterOrderNo(dto.getWaiterOrderNo());
        order.setStatus("NEW");
        order.setCreateDttm(OffsetDateTime.now());

        Long kitchenOrderId = order.getKitchenOrderId();
        kitchenOrderMapper.insert(order);
        for (OrderToDishDto d : dto.getDishes()) {

            OrderToDish otd = new OrderToDish();
            otd.setKitchenOrderId(kitchenOrderId);
            otd.setDishId(d.getDishId());
            otd.setDishesNumber(d.getDishesNumber());

            orderToDishService.create(otd);
        }
    }

    @Transactional
    public void markOrderReady(Long kitchenOrderId) {

        // 1. Получаем kitchen-заказ
        KitchenOrder order = kitchenOrderMapper.findById(kitchenOrderId);

        if (order == null) {
            throw new RuntimeException("Kitchen order not found: " + kitchenOrderId);
        }

        // 2. Меняем статус в kitchen
        order.setStatus("READY");
        kitchenOrderMapper.update(order);

        // 3. Уведомляем waiter
        OrderStatusDto dto = new OrderStatusDto(
                order.getWaiterOrderNo(), // ВАЖНО — не kitchen ID
                "READY"
        );

        waiterClient.updateOrderStatus(dto);
    }
    public KitchenOrder getById(Long id) {
        return kitchenOrderMapper.findById(id);
    }

    public List<KitchenOrder> getAll() {
        return kitchenOrderMapper.findAll();
    }

    public void create(KitchenOrder kitchenOrder) {
        kitchenOrderMapper.insert(kitchenOrder);
    }

    public void update(KitchenOrder kitchenOrder) {
        kitchenOrderMapper.update(kitchenOrder);
    }

    public void delete(Long id) {
        kitchenOrderMapper.delete(id);
    }
}

package ru.Liga.kitchen.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.Liga.dto.KitchenOrderRequestDto;
import ru.Liga.dto.OrderStatusDto;
import ru.Liga.dto.OrderToDishDto;
import ru.Liga.kitchen.entity.KitchenOrder;
import ru.Liga.kitchen.entity.OrderToDish;
import ru.Liga.kitchen.exception.NotFoundException;
import ru.Liga.kitchen.exception.BusinessException;
import ru.Liga.kitchen.feign.WaiterFeignClient;
import ru.Liga.kitchen.mapper.KitchenOrderMapper;

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
        if (dto == null || dto.getWaiterOrderNo() == null) {
            throw new BusinessException("Waiter order number is required");
        }

        KitchenOrder order = new KitchenOrder();
        order.setWaiterOrderNo(dto.getWaiterOrderNo());
        order.setStatus("NEW");
        order.setCreateDttm(OffsetDateTime.now());

        kitchenOrderMapper.insert(order);
        Long kitchenOrderId = order.getKitchenOrderId();

        if (dto.getDishes() == null || dto.getDishes().isEmpty()) {
            throw new BusinessException("Order must contain at least one dish");
        }

        for (OrderToDishDto d : dto.getDishes()) {
            if (d.getDishId() == null || d.getDishesNumber() == null || d.getDishesNumber() <= 0) {
                throw new BusinessException("Each dish must have a valid id and quantity > 0");
            }

            OrderToDish otd = new OrderToDish();
            otd.setKitchenOrderId(kitchenOrderId);
            otd.setDishId(d.getDishId());
            otd.setDishesNumber(d.getDishesNumber());

            orderToDishService.create(otd);
        }
    }


    @Transactional
    public void markOrderReady(Long kitchenOrderId) {
        if (kitchenOrderId == null || kitchenOrderId <= 0) {
            throw new BusinessException("Invalid kitchen order ID");
        }

        KitchenOrder order = kitchenOrderMapper.findById(kitchenOrderId);
        if (order == null) {
            throw new NotFoundException("Kitchen order not found for id: " + kitchenOrderId);
        }

        if ("READY".equalsIgnoreCase(order.getStatus())) {
            throw new BusinessException("Order is already READY");
        }

        order.setStatus("READY");
        kitchenOrderMapper.update(order);

        OrderStatusDto dto = new OrderStatusDto(order.getWaiterOrderNo(), "READY");
        waiterClient.updateOrderStatus(dto);
    }


    public KitchenOrder getById(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException("Invalid kitchen order ID");
        }

        KitchenOrder order = kitchenOrderMapper.findById(id);
        if (order == null) {
            throw new NotFoundException("Kitchen order not found for id: " + id);
        }

        return order;
    }


    public List<KitchenOrder> getAll() {
        return kitchenOrderMapper.findAll();
    }


    public void create(KitchenOrder kitchenOrder) {
        if (kitchenOrder == null || kitchenOrder.getWaiterOrderNo() == null) {
            throw new BusinessException("Order or waiter order number cannot be null");
        }

        kitchenOrder.setStatus("NEW");
        kitchenOrder.setCreateDttm(OffsetDateTime.now());
        kitchenOrderMapper.insert(kitchenOrder);
    }

    public void update(KitchenOrder kitchenOrder) {
        if (kitchenOrder == null || kitchenOrder.getKitchenOrderId() == null) {
            throw new BusinessException("Order or kitchen order ID cannot be null");
        }

        KitchenOrder existingOrder = kitchenOrderMapper.findById(kitchenOrder.getKitchenOrderId());
        if (existingOrder == null) {
            throw new NotFoundException("Kitchen order not found for id: " + kitchenOrder.getKitchenOrderId());
        }

        kitchenOrderMapper.update(kitchenOrder);
    }


    public void delete(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException("Invalid kitchen order ID");
        }

        KitchenOrder existingOrder = kitchenOrderMapper.findById(id);
        if (existingOrder == null) {
            throw new NotFoundException("Kitchen order not found for id: " + id);
        }

        kitchenOrderMapper.delete(id);
        orderToDishService.deleteByOrderId(id);
    }
}

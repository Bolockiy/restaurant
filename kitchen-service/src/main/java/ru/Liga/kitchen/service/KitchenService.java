package ru.Liga.kitchen.service;

import jakarta.transaction.Transactional;
import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.Liga.dto.KitchenOrderRequestDto;
import ru.Liga.dto.OrderStatusDto;
import ru.Liga.dto.OrderToDishDto;
import ru.Liga.kitchen.Kafka.KitchenKafkaProducer;
import ru.Liga.kitchen.entity.Dish;
import ru.Liga.kitchen.entity.KitchenOrder;
import ru.Liga.kitchen.entity.OrderToDish;
import ru.Liga.kitchen.exception.NotFoundException;
import ru.Liga.kitchen.exception.BusinessException;
import ru.Liga.kitchen.mapper.KitchenOrderMapper;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class KitchenService {

    private final KitchenOrderMapper kitchenOrderMapper;
    private final OrderToDishService orderToDishService;
    @Getter
    private final KitchenKafkaProducer kitchenKafkaProducer;
    private final DishService dishService;

    public KitchenService(KitchenOrderMapper kitchenOrderMapper,  OrderToDishService orderToDishService, KitchenKafkaProducer kitchenKafkaProducer, DishService dishService) {
        this.kitchenOrderMapper = kitchenOrderMapper;
        this.orderToDishService = orderToDishService;
        this.kitchenKafkaProducer = kitchenKafkaProducer;
        this.dishService = dishService;
    }


    @Transactional
    public void processOrderFromWaiter(KitchenOrderRequestDto dto) {
        System.out.println("Processing order: " + dto.getWaiterOrderNo());

        if (dto.getWaiterOrderNo() == null) {
            throw new BusinessException("Waiter order number is required");
        }

        if (dto.getDishes() == null || dto.getDishes().isEmpty()) {
            throw new BusinessException("Order must contain at least one dish");
        }

        KitchenOrder order = new KitchenOrder();
        order.setWaiterOrderNo(dto.getWaiterOrderNo());
        order.setStatus("RECEIVED");
        order.setCreateDttm(OffsetDateTime.now());

        kitchenOrderMapper.insert(order);
        Long kitchenOrderId = order.getKitchenOrderId();

        for (OrderToDishDto d : dto.getDishes()) {
            if (d.getDishId() == null || d.getDishesNumber() == null || d.getDishesNumber() <= 0) {
                throw new BusinessException("Each dish must have valid id and quantity > 0");
            }

            OrderToDish otd = new OrderToDish();
            otd.setKitchenOrderId(kitchenOrderId);
            otd.setDishId(d.getDishId());
            otd.setDishesNumber(d.getDishesNumber());

            orderToDishService.create(otd);
        }

        System.out.println("Order saved to kitchen DB");
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
        kitchenKafkaProducer.sendStatusToWaiter(dto);
    }
    public boolean checkProductsAvailability(KitchenOrderRequestDto dto) {
        for (OrderToDishDto dish : dto.getDishes()) {
            Dish d =  dishService.getById(dish.getDishId());
            if (d == null || d.getBalance() < dish.getDishesNumber()) {
                return false;
            }
        }
        return true;
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

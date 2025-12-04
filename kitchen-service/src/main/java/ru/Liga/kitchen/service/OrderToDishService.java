package ru.Liga.kitchen.service;

import org.springframework.stereotype.Service;
import ru.Liga.kitchen.entity.OrderToDish;
import ru.Liga.kitchen.mapper.OrderToDishMapper;

import java.util.List;

@Service
public class OrderToDishService {

    private final OrderToDishMapper mapper;

    public OrderToDishService(OrderToDishMapper mapper) {
        this.mapper = mapper;
    }

    public List<OrderToDish> getByOrderId(Long orderId) {
        return mapper.findByOrderId(orderId);
    }

    public OrderToDish getOne(Long kitchenOrderId, Long dishId) {
        return mapper.findOne(kitchenOrderId, dishId);
    }

    public void create(OrderToDish orderToDish) {
        mapper.insert(orderToDish);
    }

    public void update(OrderToDish orderToDish) {
        mapper.update(orderToDish);
    }

    public void delete(Long kitchenOrderId, Long dishId) {
        mapper.delete(kitchenOrderId, dishId);
    }

    public void deleteByOrderId(Long kitchenOrderId) {
        mapper.deleteByOrderId(kitchenOrderId);
    }
}

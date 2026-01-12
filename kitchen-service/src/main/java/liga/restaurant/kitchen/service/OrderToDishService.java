package liga.restaurant.kitchen.service;

import liga.restaurant.kitchen.entity.OrderToDish;
import liga.restaurant.kitchen.mapper.OrderToDishMapper;
import org.springframework.stereotype.Service;

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

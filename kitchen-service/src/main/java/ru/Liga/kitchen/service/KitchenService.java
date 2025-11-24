package ru.Liga.kitchen.service;

import org.springframework.stereotype.Service;
import ru.Liga.dto.OrderDto;
import java.util.*;

@Service
public class KitchenService {

    private final Map<Long, OrderDto> orders = new HashMap<>();

    public String accept(Long orderId) {
        return setStatus(orderId, "ACCEPTED");
    }

    public String reject(Long orderId) {
        return setStatus(orderId, "REJECTED");
    }

    public String ready(Long orderId) {
        return setStatus(orderId, "READY");
    }

    public List<OrderDto> getAll() {
        return new ArrayList<>(orders.values());
    }

    private String setStatus(Long id, String status) {
        OrderDto dto = orders.computeIfAbsent(id, x -> new OrderDto(id, "unknown", status));
        dto.setStatus(status);
        return "Order " + id + " → " + status;
    }
}

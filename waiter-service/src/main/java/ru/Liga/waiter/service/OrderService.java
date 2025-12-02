//package ru.Liga.waiter.service;
//
//import org.springframework.stereotype.Service;
//import ru.Liga.dto.OrderDto;
//import java.util.*;
//import java.util.concurrent.atomic.AtomicLong;
//
//@Service
//public class OrderService {
//
//    private final Map<Long, OrderDto> orders = new HashMap<>();
//    private final AtomicLong idGen = new AtomicLong(1);
//
//    public List<OrderDto> getAll() {
//        return new ArrayList<>(orders.values());
//    }
//
//    public OrderDto getById(Long id) {
//        return orders.get(id);
//    }
//
//    public OrderDto create(OrderDto dto) {
//        Long id = idGen.getAndIncrement();
//        dto.setId(id);
//        dto.setStatus("NEW");
//        orders.put(id, dto);
//        return dto;
//    }
//
//    public String getStatus(Long id) {
//        OrderDto dto = orders.get(id);
//        return dto != null ? dto.getStatus() : null;
//    }
//}

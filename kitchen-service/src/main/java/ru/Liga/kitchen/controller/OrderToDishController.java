package ru.Liga.kitchen.controller;

import org.springframework.web.bind.annotation.*;
import ru.Liga.kitchen.entity.OrderToDish;
import ru.Liga.kitchen.service.OrderToDishService;

import java.util.List;

@RestController
@RequestMapping("/kitchen/order-to-dish")
public class OrderToDishController {

    private final OrderToDishService service;

    public OrderToDishController(OrderToDishService service) {
        this.service = service;
    }

    @GetMapping("/order/{orderId}")
    public List<OrderToDish> getByOrderId(@PathVariable Long orderId) {
        return service.getByOrderId(orderId);
    }

    @GetMapping("/{orderId}/{dishId}")
    public OrderToDish getOne(@PathVariable Long orderId,
                              @PathVariable Long dishId) {
        return service.getOne(orderId, dishId);
    }

    @PostMapping
    public void create(@RequestBody OrderToDish orderToDish) {
        service.create(orderToDish);
    }

    @PutMapping
    public void update(@RequestBody OrderToDish orderToDish) {
        service.update(orderToDish);
    }

    @DeleteMapping("/{orderId}/{dishId}")
    public void delete(@PathVariable Long orderId,
                       @PathVariable Long dishId) {
        service.delete(orderId, dishId);
    }

    @DeleteMapping("/order/{orderId}")
    public void deleteByOrder(@PathVariable Long orderId) {
        service.deleteByOrderId(orderId);
    }
}

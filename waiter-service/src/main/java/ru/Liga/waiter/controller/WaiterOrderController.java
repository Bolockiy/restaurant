package ru.Liga.waiter.controller;

import org.springframework.web.bind.annotation.*;
import ru.Liga.waiter.entity.WaiterOrder;
import ru.Liga.waiter.service.WaiterOrderService;

import java.util.List;

@RestController
@RequestMapping("/waiter/orders")
public class WaiterOrderController {

    private final WaiterOrderService service;

    public WaiterOrderController(WaiterOrderService service) {
        this.service = service;
    }

    @PostMapping
    public WaiterOrder create(@RequestBody WaiterOrder order) {
        return service.createOrder(order);
    }

    @GetMapping
    public List<WaiterOrder> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public WaiterOrder getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

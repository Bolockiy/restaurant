package ru.Liga.waiter.controller;

import org.springframework.web.bind.annotation.*;
import ru.Liga.waiter.entity.OrderPosition;
import ru.Liga.waiter.service.OrderPositionService;

import java.util.List;

@RestController
@RequestMapping("/waiter/order-positions")
public class OrderPositionController {

    private final OrderPositionService service;

    public OrderPositionController(OrderPositionService service) {
        this.service = service;
    }

    @PostMapping
    public OrderPosition create(@RequestBody OrderPosition position) {
        return service.save(position);
    }

    @GetMapping
    public List<OrderPosition> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public OrderPosition getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

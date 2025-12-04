package ru.Liga.waiter.controller;

import org.springframework.web.bind.annotation.*;
import ru.Liga.dto.KitchenOrderRequestDto;
import ru.Liga.dto.WaiterOrderDto;
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

    @PostMapping("/status")
    public void createKitchen(@RequestBody KitchenOrderRequestDto order) {
        service.createOrderKitchen(order);
    }


    @GetMapping
    public List<WaiterOrderDto> getAllOrders() {
        return service.findAllDto();
    }

    @GetMapping("/{id}")
    public WaiterOrderDto getOrderById(@PathVariable Long id) {
        return service.findByIdDto(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

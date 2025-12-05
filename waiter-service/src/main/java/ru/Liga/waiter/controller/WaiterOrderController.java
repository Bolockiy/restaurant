package ru.Liga.waiter.controller;

import org.springframework.web.bind.annotation.*;
import ru.Liga.dto.KitchenOrderRequestDto;
import ru.Liga.dto.OrderStatusDto;
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

    @PostMapping("/order")
    public void createKitchen(@RequestBody KitchenOrderRequestDto order) {
        service.createOrderKitchen(order);
    }


    @GetMapping
    public List<WaiterOrderDto> getAllOrders() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public WaiterOrderDto getOrderById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
    @PostMapping("/status")
    public void updateStatus(@RequestBody OrderStatusDto dto) {
        service.updateOrderStatus(
                dto.getWaiterOrderNo(),
                dto.getStatus()
        );
    }
}

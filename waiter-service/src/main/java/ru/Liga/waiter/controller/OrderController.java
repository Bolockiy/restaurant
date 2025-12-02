//package ru.Liga.waiter.controller;
//
//import org.springframework.web.bind.annotation.*;
//import ru.Liga.dto.OrderDto;
//import ru.Liga.waiter.service.OrderService;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/waiter/orders/")
//public class OrderController {
//
//    private final OrderService service;
//
//    public OrderController(OrderService service) {
//        this.service = service;
//    }
//
//    @GetMapping
//    public List<OrderDto> getAll() {
//        return service.getAll();
//    }
//
//    @GetMapping("/{id}")
//    public OrderDto getById(@PathVariable Long id) {
//        return service.getById(id);
//    }
//
//    @PostMapping
//    public OrderDto create(@RequestBody OrderDto dto) {
//        return service.create(dto);
//    }
//
//    @GetMapping("/{id}/status")
//    public String getStatus(@PathVariable Long id) {
//        return service.getStatus(id);
//    }
//}

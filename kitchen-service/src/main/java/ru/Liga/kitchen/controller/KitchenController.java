package ru.Liga.kitchen.controller;

import org.springframework.web.bind.annotation.*;
import ru.Liga.dto.KitchenDto;
import ru.Liga.dto.OrderDto;
import ru.Liga.kitchen.service.KitchenService;

import java.util.List;

@RestController
@RequestMapping("/kitchen")
public class KitchenController {

    private final KitchenService service;

    public KitchenController(KitchenService service) {
        this.service = service;
    }

    @GetMapping("/orders")
    public List<OrderDto> getAll() {
        return service.getAll();
    }

    @PostMapping
    public String updateStatus(@RequestBody KitchenDto dto) {
        Long id = dto.getOrderId();
        String action = dto.getAction();

        return switch (action.toUpperCase()) {
            case "ACCEPT" -> service.accept(id);
            case "REJECT" -> service.reject(id);
            case "READY"  -> service.ready(id);
            default -> "Unknown action: " + action;
        };
    }
}

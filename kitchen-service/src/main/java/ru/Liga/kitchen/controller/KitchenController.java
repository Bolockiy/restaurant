package ru.Liga.kitchen.controller;

import org.springframework.web.bind.annotation.*;
import ru.Liga.kitchen.entity.KitchenOrder;
import ru.Liga.kitchen.service.KitchenService;

import java.util.List;

@RestController
@RequestMapping("/kitchen/Order")
public class KitchenController {

    private final KitchenService kitchenService;

    public KitchenController(KitchenService kitchenService) {
        this.kitchenService = kitchenService;
    }

    @GetMapping("/{id}")
    public KitchenOrder getById(@PathVariable Long id) {
        return kitchenService.getById(id);
    }

    @GetMapping
    public List<KitchenOrder> getAll() {
        return kitchenService.getAll();
    }

    @PostMapping
    public void create(@RequestBody KitchenOrder kitchenOrder) {
        kitchenService.create(kitchenOrder);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody KitchenOrder kitchenOrder) {
        kitchenOrder.setKitchen_order_id(id);
        kitchenService.update(kitchenOrder);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        kitchenService.delete(id);
    }
}

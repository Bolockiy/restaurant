package ru.Liga.kitchen.controller;

import org.springframework.web.bind.annotation.*;
import ru.Liga.kitchen.entity.Dish;
import ru.Liga.kitchen.service.DishService;

import java.util.List;

@RestController
@RequestMapping("/kitchen/dishes")
public class DishController {

    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/{id}")
    public Dish getById(@PathVariable Long id) {
        return dishService.getById(id);
    }

    @GetMapping
    public List<Dish> getAll() {
        return dishService.getAll();
    }

    @PostMapping
    public void create(@RequestBody Dish dish) {
        dishService.create(dish);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody Dish dish) {
        dish.setDishId(id);
        dishService.update(dish);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        dishService.delete(id);
    }
}

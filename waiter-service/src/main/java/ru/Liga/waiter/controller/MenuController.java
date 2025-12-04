package ru.Liga.waiter.controller;

import org.springframework.web.bind.annotation.*;
import ru.Liga.waiter.entity.Menu;
import ru.Liga.waiter.service.MenuService;

import java.util.List;

@RestController
@RequestMapping("/waiter/menu")
public class MenuController {

    private final MenuService service;

    public MenuController(MenuService service) {
        this.service = service;
    }

    @PostMapping
    public Menu create(@RequestBody Menu menu) {
        return service.save(menu);
    }

    @GetMapping
    public List<Menu> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Menu getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

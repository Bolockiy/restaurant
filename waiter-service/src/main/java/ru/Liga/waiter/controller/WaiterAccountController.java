package ru.Liga.waiter.controller;

import org.springframework.web.bind.annotation.*;
import ru.Liga.dto.WaiterAccountDto;
import ru.Liga.waiter.service.WaiterAccountService;

import java.util.List;

@RestController
@RequestMapping("/waiter/accounts")
public class WaiterAccountController {
    private final WaiterAccountService waiterService;

    public WaiterAccountController(WaiterAccountService waiterService) {
        this.waiterService = waiterService;
    }

    // Создание нового аккаунта
    @PostMapping
    public WaiterAccountDto createAccount(@RequestBody WaiterAccountDto dto) {
        return waiterService.save(dto);
    }

    // Получение всех аккаунтов
    @GetMapping("/all")
    public List<WaiterAccountDto> findAll() {
        return waiterService.findAll();
    }

}

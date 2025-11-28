package ru.Liga.waiter.controller;

import org.springframework.web.bind.annotation.*;
import ru.Liga.dto.WaiterAccountDto;
import ru.Liga.waiter.service.WaiterAccountService;

import java.util.List;

@RestController
@RequestMapping("/waiter/accounts/")

public class WaiterAccountController {
    private WaiterAccountService waiterService;
    WaiterAccountController(WaiterAccountService waiterService)
    {
        waiterService = waiterService;
    }

    @PostMapping
    public WaiterAccountDto createAccount(@RequestBody WaiterAccountDto dto)
    {
        return waiterService.save(dto);
    }
    @GetMapping("/{name}")
    public WaiterAccountDto findAccountByName(@PathVariable String name)
    {
        return  waiterService.findAccountByName(name);
    }
    @PostMapping
    public WaiterAccountDto updateAccount(@RequestBody WaiterAccountDto dto)
    {
        return waiterService.save(dto);
    }

    @GetMapping("/all")
    public List<WaiterAccountDto> findAll()
    {
        return waiterService.findAll();
    }

    @DeleteMapping
    public WaiterAccountDto deleteAccount(@RequestParam String name)
    {
        return waiterService.delete(name);
    }
}

package ru.Liga.waiter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<WaiterAccountDto> createAccount(@RequestBody WaiterAccountDto dto) {
        WaiterAccountDto saved = waiterService.save(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<WaiterAccountDto>> findAll() {
        List<WaiterAccountDto> accounts = waiterService.findAll();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WaiterAccountDto> findById(@PathVariable Long id) {
        WaiterAccountDto dto = waiterService.findById(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WaiterAccountDto> update(@PathVariable Long id, @RequestBody WaiterAccountDto dto) {
        WaiterAccountDto updated = waiterService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = waiterService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<WaiterAccountDto>> findByName(@RequestParam String name) {
        List<WaiterAccountDto> accounts = waiterService.findByName(name);
        return ResponseEntity.ok(accounts);
    }
}

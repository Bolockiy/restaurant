package ru.Liga.waiter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.Liga.dto.WaiterAccountDto;
import ru.Liga.waiter.service.WaiterAccountService;

import java.util.List;

@RestController
@RequestMapping("/waiter/accounts")
@Tag(name = "Waiter Accounts API", description = "Управление аккаунтами официантов")
public class WaiterAccountController {

    private final WaiterAccountService waiterService;

    public WaiterAccountController(WaiterAccountService waiterService) {
        this.waiterService = waiterService;
    }

    @Operation(
            summary = "Создать аккаунт официанта",
            description = "Создает нового официанта в системе"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Аккаунт создан",
                    content = @Content(schema = @Schema(implementation = WaiterAccountDto.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные")
    })
    @PostMapping
    public ResponseEntity<WaiterAccountDto> createAccount(
            @RequestBody WaiterAccountDto dto
    ) {
        WaiterAccountDto saved = waiterService.save(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Получить всех официантов",
            description = "Возвращает список всех официантов"
    )
    @ApiResponse(responseCode = "200", description = "Список официантов получен")
    @GetMapping("/all")
    public ResponseEntity<List<WaiterAccountDto>> findAll() {
        List<WaiterAccountDto> accounts = waiterService.findAll();
        return ResponseEntity.ok(accounts);
    }

    @Operation(
            summary = "Получить официанта по ID",
            description = "Возвращает официанта по его идентификатору"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Официант найден"),
            @ApiResponse(responseCode = "404", description = "Официант не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<WaiterAccountDto> findById(
            @Parameter(description = "ID официанта", example = "1")
            @PathVariable Long id
    ) {
        WaiterAccountDto dto = waiterService.findById(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Обновить данные официанта",
            description = "Обновляет информацию об официанте по ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Данные обновлены"),
            @ApiResponse(responseCode = "404", description = "Официант не найден")
    })
    @PutMapping("/{id}")
    public ResponseEntity<WaiterAccountDto> update(
            @Parameter(description = "ID официанта", example = "1")
            @PathVariable Long id,
            @RequestBody WaiterAccountDto dto
    ) {
        WaiterAccountDto updated = waiterService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @Operation(
            summary = "Удалить официанта",
            description = "Удаляет официанта по ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Удалено"),
            @ApiResponse(responseCode = "404", description = "Официант не найден")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID официанта", example = "1")
            @PathVariable Long id
    ) {
        boolean deleted = waiterService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Поиск официантов по имени",
            description = "Возвращает список официантов по части имени"
    )
    @ApiResponse(responseCode = "200", description = "Официанты найдены")
    @GetMapping("/search")
    public ResponseEntity<List<WaiterAccountDto>> findByName(
            @Parameter(description = "Имя официанта", example = "Иван")
            @RequestParam String name
    ) {
        List<WaiterAccountDto> accounts = waiterService.findByName(name);
        return ResponseEntity.ok(accounts);
    }
}

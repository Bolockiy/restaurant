package liga.restaurant.waiter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import liga.restaurant.dto.WaiterAccountDto;
import liga.restaurant.waiter.entity.WaiterAccount;
import liga.restaurant.waiter.service.WaiterAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/waiter/accounts")
@Tag(name = "Waiter Accounts API", description = "Управление аккаунтами официантов")
@RequiredArgsConstructor
public class WaiterAccountController {

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
    public WaiterAccount createAccount(
            @Valid @RequestBody WaiterAccount dto
    ) {
        return waiterService.save(dto);
    }

    @Operation(
            summary = "Получить всех официантов",
            description = "Возвращает список всех официантов"
    )
    @ApiResponse(responseCode = "200", description = "Список официантов получен")
    @GetMapping("/all")
    public Page<WaiterAccountDto> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return waiterService.findAll(PageRequest.of(page, size));
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
    public WaiterAccountDto findById(
            @Parameter(description = "ID официанта", example = "1")
            @PathVariable Long id
    ) {
        return waiterService.findById(id);
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
    public WaiterAccount update(
            @Parameter(description = "ID официанта", example = "1")
            @PathVariable Long id,
            @RequestBody WaiterAccountDto dto
    ) {
        return waiterService.update(id, dto);
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
    public void delete(
            @Parameter(description = "ID официанта", example = "1")
            @PathVariable Long id
    ) {
        waiterService.delete(id);
    }

    @Operation(
            summary = "Поиск официантов по имени",
            description = "Возвращает список официантов по части имени"
    )
    @ApiResponse(responseCode = "200", description = "Официанты найдены")
    @GetMapping("/search")
    public WaiterAccountDto findByName(
            @Parameter(description = "Имя официанта", example = "Иван")
            @RequestParam String name
    ) {
        return waiterService.findByName(name);
    }

    private final WaiterAccountService waiterService;
}

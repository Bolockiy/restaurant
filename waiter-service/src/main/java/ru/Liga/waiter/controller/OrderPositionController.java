package ru.Liga.waiter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.Liga.waiter.entity.OrderPosition;
import ru.Liga.waiter.service.OrderPositionService;

import java.util.List;

@RestController
@RequestMapping("/waiter/order-positions")
@Tag(name = "Order Position API", description = "Позиции блюд в заказе официанта")
public class OrderPositionController {

    private final OrderPositionService service;

    public OrderPositionController(OrderPositionService service) {
        this.service = service;
    }

    @Operation(
            summary = "Создать позицию заказа",
            description = "Добавляет новую позицию блюда в заказ официанта"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Позиция успешно создана"),
            @ApiResponse(responseCode = "400", description = "Ошибка входных данных")
    })
    @PostMapping
    public OrderPosition create(@RequestBody OrderPosition position) {
        return service.save(position);
    }

    @Operation(
            summary = "Получить все позиции заказов",
            description = "Возвращает список всех позиций всех заказов официанта"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список успешно получен")
    })
    @GetMapping
    public List<OrderPosition> getAll() {
        return service.findAll();
    }

    @Operation(
            summary = "Получить позицию заказа по ID",
            description = "Возвращает одну позицию блюда по её идентификатору"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Позиция найдена"),
            @ApiResponse(responseCode = "404", description = "Позиция не найдена")
    })
    @GetMapping("/{id}")
    public OrderPosition getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(
            summary = "Удалить позицию заказа",
            description = "Удаляет позицию блюда из заказа официанта"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Позиция удалена"),
            @ApiResponse(responseCode = "404", description = "Позиция не найдена")
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

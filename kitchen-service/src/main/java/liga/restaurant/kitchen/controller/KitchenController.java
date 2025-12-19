package liga.restaurant.kitchen.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import liga.restaurant.dto.KitchenOrderRequestDto;
import liga.restaurant.kitchen.entity.KitchenOrder;
import liga.restaurant.kitchen.service.KitchenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kitchen/orders")
@Tag(name = "Kitchen API", description = "Работа с заказами кухни")
@RequiredArgsConstructor
public class KitchenController {

    @Operation(
            summary = "Получить заказ кухни по ID",
            description = "Возвращает один заказ кухни по его идентификатору"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Заказ найден"),
            @ApiResponse(responseCode = "404", description = "Заказ не найден"),
            @ApiResponse(responseCode = "400", description = "Некорректный ID")
    })
    @GetMapping("/{id}")
    public KitchenOrder getById(
            @Parameter(description = "ID заказа кухни", example = "1")
            @PathVariable Long id
    ) {
        return kitchenService.getById(id);
    }

    @Operation(
            summary = "Получить все заказы кухни",
            description = "Возвращает список всех заказов кухни"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список заказов получен")
    })
    @GetMapping
    public List<KitchenOrder> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return kitchenService.getAll(page, size);
    }

    @Operation(
            summary = "Принять заказ от официанта (через Kafka)",
            description = "Используется для внутренней передачи заказов из waiter-service"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Заказ принят кухней"),
            @ApiResponse(responseCode = "400", description = "Ошибка в данных заказа")
    })
    @PostMapping
    public void create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO заказа от официанта",
                    required = true
            )
            @Valid @RequestBody KitchenOrderRequestDto kitchenOrder
    ) {
        kitchenService.processOrderFromWaiter(kitchenOrder);
    }

    @Operation(
            summary = "Отметить заказ как READY",
            description = "Меняет статус заказа кухни и отправляет статус официанту"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Заказ отмечен как READY"),
            @ApiResponse(responseCode = "404", description = "Заказ не найден")
    })
    @PostMapping("ready/{id}")
    public void markReady(
            @Parameter(description = "ID заказа кухни", example = "10")
            @PathVariable Long id
    ) {
        kitchenService.markOrderReady(id);
    }

    @Operation(
            summary = "Обновить заказ кухни",
            description = "Обновляет данные заказа кухни по ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Заказ обновлён"),
            @ApiResponse(responseCode = "404", description = "Заказ не найден")
    })
    @PutMapping("/{id}")
    public void update(
            @Parameter(description = "ID заказа кухни", example = "5")

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Обновлённые данные заказа",
                    required = true
            )
            @Valid @RequestBody KitchenOrder kitchenOrder
    ) {

        kitchenService.update(kitchenOrder);
    }

    @Operation(
            summary = "Удалить заказ кухни",
            description = "Полностью удаляет заказ кухни по ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Заказ удалён"),
            @ApiResponse(responseCode = "404", description = "Заказ не найден")
    })
    @DeleteMapping("/{id}")
    public void delete(
            @Parameter(description = "ID заказа кухни", example = "7")
            @PathVariable Long id
    ) {
        kitchenService.delete(id);
    }

    private final KitchenService kitchenService;
}



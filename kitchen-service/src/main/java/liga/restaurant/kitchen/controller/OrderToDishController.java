package liga.restaurant.kitchen.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import liga.restaurant.kitchen.entity.OrderToDish;
import liga.restaurant.kitchen.service.OrderToDishService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kitchen/order-to-dish")
@Tag(name = "Order-To-Dish API", description = "Связь заказов кухни с блюдами")
@RequiredArgsConstructor
public class OrderToDishController {
    private final OrderToDishService service;
    @Operation(
            summary = "Получить все блюда по заказу",
            description = "Возвращает список блюд, входящих в заказ кухни"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список найден"),
            @ApiResponse(responseCode = "404", description = "Заказ не найден")
    })
    @GetMapping("/{orderId}")
    public List<OrderToDish> getByOrderId(@PathVariable Long orderId) {
        return service.getByOrderId(orderId);
    }

    @Operation(
            summary = "Добавить блюдо в заказ",
            description = "Добавляет новую связь между заказом и блюдом"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Блюдо добавлено"),
            @ApiResponse(responseCode = "400", description = "Ошибка входных данных")
    })
    @PostMapping
    public void create(@Valid @RequestBody OrderToDish orderToDish) {
        service.create(orderToDish);
    }

    @Operation(
            summary = "Обновить количество блюда в заказе",
            description = "Обновляет количество порций блюда в заказе"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Обновлено"),
            @ApiResponse(responseCode = "404", description = "Связь не найдена")
    })
    @PutMapping
    public void update(@Valid @RequestBody OrderToDish orderToDish) {
        service.update(orderToDish);
    }

    @Operation(
            summary = "Удалить блюдо из заказа",
            description = "Удаляет конкретное блюдо из заказа кухни"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Удалено"),
            @ApiResponse(responseCode = "404", description = "Связь не найдена")
    })
    @DeleteMapping("/{orderId}/{dishId}")
    public void delete(@PathVariable Long orderId,
                       @PathVariable Long dishId) {
        service.delete(orderId, dishId);
    }
}

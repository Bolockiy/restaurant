package liga.restaurant.waiter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import liga.restaurant.dto.CreateWaiterOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import liga.restaurant.dto.KitchenOrderRequestDto;
import liga.restaurant.dto.OrderStatusDto;
import liga.restaurant.dto.WaiterOrderDto;
import liga.restaurant.waiter.entity.WaiterOrder;
import liga.restaurant.waiter.service.WaiterOrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@RestController
@RequestMapping("/waiter/orders")
@Tag(name = "Waiter API", description = "Операции с заказами официантов")
@RequiredArgsConstructor
public class WaiterOrderController {
    private final WaiterOrderService service;

    @Operation(
            summary = "Создать заказ официанта",
            description = "Создаёт новый заказ официанта вместе с позициями"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Заказ успешно создан"),
            @ApiResponse(responseCode = "400", description = "Неверные данные заказа")
    })
    @PostMapping
    public WaiterOrder create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Сущность заказа",
                    required = true
            )
            @RequestBody CreateWaiterOrderDto order
    ) {
        return service.createOrderKitchen(order);
    }
    @Operation(
            summary = "Получить все заказы официантов",
            description = "Возвращает список всех заказов"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список заказов получен")
    })
    @GetMapping
    public Page<WaiterOrderDto> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.findAll(PageRequest.of(page, size));
    }
    @Operation(
            summary = "Получить заказ официанта по ID",
            description = "Возвращает заказ официанта с позициями"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Заказ найден"),
            @ApiResponse(responseCode = "404", description = "Заказ не найден")
    })
    @GetMapping("/{id}")
    public WaiterOrderDto getOrderById(
            @Parameter(description = "ID заказа официанта", example = "15")
            @PathVariable Long id
    ) {
        return service.findById(id);
    }

    @Operation(
            summary = "Удалить заказ официанта",
            description = "Полностью удаляет заказ и все позиции"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Заказ удалён"),
            @ApiResponse(responseCode = "404", description = "Заказ не найден")
    })
    @DeleteMapping("/{id}")
    public void delete(
            @Parameter(description = "ID заказа официанта", example = "11")
            @PathVariable Long id
    ) {
        service.delete(id);
    }

    @Operation(
            summary = "Обновить статус заказа",
            description = "Меняет статус заказа и отправляет событие"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Статус обновлён"),
            @ApiResponse(responseCode = "400", description = "Неверные данные статуса")
    })
    @PostMapping("/status")
    public void updateStatus(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO изменения статуса заказа",
                    required = true
            )
            @RequestBody OrderStatusDto dto
    ) {
        service.updateOrderStatus(dto.getWaiterOrderNo(), dto.getStatus());
    }
}

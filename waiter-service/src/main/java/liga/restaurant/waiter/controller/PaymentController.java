package liga.restaurant.waiter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import liga.restaurant.waiter.entity.Payment;
import liga.restaurant.waiter.service.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/waiter/payments")
@Tag(name = "Payment API", description = "Управление оплатами заказов официанта")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService service;

    @Operation(
            summary = "Создать оплату",
            description = "Создаёт новую оплату для заказа официанта"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Оплата успешно создана"),
            @ApiResponse(responseCode = "400", description = "Ошибка входных данных")
    })
    @PostMapping
    public Payment create(@RequestBody Payment payment) {
        return service.save(payment);
    }

    @Operation(
            summary = "Получить все оплаты",
            description = "Возвращает список всех оплат официанта"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список успешно получен")
    })
    @GetMapping
    public List<Payment> getAll() {
        return service.findAll();
    }

    @Operation(
            summary = "Получить оплату по ID заказа",
            description = "Возвращает оплату по номеру заказа"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Оплата найдена"),
            @ApiResponse(responseCode = "404", description = "Оплата не найдена")
    })
    @GetMapping("/{id}")
    public Payment getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(
            summary = "Удалить оплату",
            description = "Удаляет оплату по номеру заказа"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Оплата удалена"),
            @ApiResponse(responseCode = "404", description = "Оплата не найдена")
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

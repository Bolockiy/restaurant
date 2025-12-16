package liga.restaurant.waiter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import liga.restaurant.waiter.entity.Menu;
import liga.restaurant.waiter.service.MenuService;

import java.util.List;

@RestController
@RequestMapping("/waiter/menu")
@RequiredArgsConstructor
public class MenuController {

    @Operation (
            summary = "Создание меню",
            description = "Создает меню по заданному json"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Меню создано"),
            @ApiResponse(responseCode = "400", description = "В запросе ошибка")
    })

    @PostMapping
    public Menu create( @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "DTO меню",
            required = true
    ) @RequestBody Menu menu) {
        return service.save(menu);
    }

    @Operation(
            summary = "Получить все меню",
            description = "Возвращает список всех меню"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список меню получен")
    })
    @GetMapping
    public List<Menu> getAll() {
        return service.findAll();
    }

    @Operation(
            summary = "Получить меню по ID",
            description = "Возвращает одно меню по его идентификатору"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "меню найден"),
            @ApiResponse(responseCode = "404", description = "Меню не найдено"),
            @ApiResponse(responseCode = "400", description = "Некорректный ID")
    })

    @GetMapping("/{id}")
    public Menu getById(   @Parameter(description = "ID меню", example = "1")
                               @PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(
            summary = "Удалить меню",
            description = "Полностью удаляет меню по ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "меню удалено"),
            @ApiResponse(responseCode = "404", description = "Меню не найдено")
    })

    @DeleteMapping("/{id}")
    public void delete(
            @Parameter(description = "ID меню", example = "7")
            @PathVariable Long id
    )
    {
        service.delete(id);
    }

    private final MenuService service;
}

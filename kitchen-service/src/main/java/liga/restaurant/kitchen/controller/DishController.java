package liga.restaurant.kitchen.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import liga.restaurant.kitchen.entity.Dish;
import liga.restaurant.kitchen.service.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kitchen/dishes")
@Tag(name = "Kitchen Dishes API", description = "Работа с блюдами кухни")

@RequiredArgsConstructor
public class DishController {

    @Operation(
            summary = "Получить блюдо по ID",
            description = "Возвращает одно блюдо по его идентификатору"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Блюдо найдено"),
            @ApiResponse(responseCode = "404", description = "Блюдо не найдено"),
            @ApiResponse(responseCode = "400", description = "Некорректный ID")
    })
    @GetMapping("/{id}")
    public Dish getById(
            @Parameter(description = "ID блюда", example = "1")
            @PathVariable Long id
    ) {
        return dishService.getById(id);
    }

    @Operation(
            summary = "Получить все блюда",
            description = "Возвращает список всех блюд кухни"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список блюд получен")
    })
    @GetMapping
    public List<Dish> getAll() {
        return dishService.getAll();
    }

    @Operation(
            summary = "Создать новое блюдо",
            description = "Добавляет новое блюдо в меню кухни"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Блюдо успешно создано"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации данных")
    })
    @PostMapping
    public void create(
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Сущность блюда",
                    required = true
            )
            @RequestBody Dish dish
    ) {
        dishService.create(dish);
    }

    @Operation(
            summary = "Обновить блюдо",
            description = "Обновляет данные блюда по ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Блюдо обновлено"),
            @ApiResponse(responseCode = "404", description = "Блюдо не найдено"),
            @ApiResponse(responseCode = "400", description = "Ошибка в данных")
    })
    @PutMapping("/{id}")
    public void update(
            @Valid
            @Parameter(description = "ID блюда", example = "3")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Обновлённые данные блюда",
                    required = true
            )
            @RequestBody Dish dish
    ) {
        dish.setDishId(id);
        dishService.update(dish);
    }

    @Operation(
            summary = "Удалить блюдо",
            description = "Удаляет блюдо по ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Блюдо удалено"),
            @ApiResponse(responseCode = "404", description = "Блюдо не найдено")
    })
    @DeleteMapping("/{id}")
    public void delete(
            @Parameter(description = "ID блюда", example = "5")
            @PathVariable Long id
    ) {
        dishService.delete(id);
    }

    private final DishService dishService;
}
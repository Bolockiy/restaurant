package liga.restaurant.kitchen.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import liga.restaurant.kitchen.entity.Dish;
import liga.restaurant.kitchen.mapper.DishMapper;
import org.springframework.transaction.annotation.Transactional;
import liga.restaurant.BusinessException;
import liga.restaurant.NotFoundException;

import java.util.List;

@Service
@Slf4j
public class DishService {

    public DishService(DishMapper dishMapper) {
        this.dishMapper = dishMapper;
    }

    public Dish getById(Long id) {
        return dishMapper.findById(id);
    }

    public List<Dish> getAll() {
        return dishMapper.findAll();
    }

    public void create(Dish dish) {
        dishMapper.insert(dish);
    }

    public void update(Dish dish) {
        dishMapper.update(dish);
    }

    public void delete(Long id) {
        dishMapper.delete(id);
    }

    public void checkAvailability(Long dishId, Long amount) {
        Dish dish = dishMapper.findByIdForUpdate(dishId);

        if (dish == null) {
            throw new NotFoundException("Блюдо не найдено: " + dishId);
        }

        if (dish.getBalance() < amount) {
            throw new BusinessException(
                    "Недостаточно блюда '" + dish.getName() +
                            "'. Остаток=" + dish.getBalance() +
                            ", требуется=" + amount
            );
        }
    }

    /**
     * Помечает заказ кухни как READY (готов).
     * После этого отправляет статус официанту через Kafka.
     *
     * @param dishId идентификатор блюда
     * @param amount кол-во блюд
     *
     * @throws BusinessException если недостаточно блюда
     * @throws NotFoundException если блюдо не найдено
     */

    @Transactional
    public void decreaseBalance(Long dishId, Long amount) {
        dishMapper.decreaseBalance(dishId, amount);
        log.info("Balance decreased: dishId={}, amount={}",
                dishId, amount);
    }
    private final DishMapper dishMapper;
}

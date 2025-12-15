package liga.restaurant.kitchen.service;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import liga.restaurant.kitchen.entity.Dish;
import liga.restaurant.kitchen.mapper.DishMapper;
import java.util.List;

@Service
public class DishService {
    private final DishMapper dishMapper;

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
}

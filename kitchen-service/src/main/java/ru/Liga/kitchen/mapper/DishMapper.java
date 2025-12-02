package ru.Liga.kitchen.mapper;

import org.apache.ibatis.annotations.*;
import ru.Liga.kitchen.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishMapper {

    @Select("SELECT dish_id, balance, short_name, dish_composition FROM kitchen.dish WHERE dish_id = #{id}")
    Dish findById(Long id);

    @Select("SELECT dish_id, balance, short_name, dish_composition FROM kitchen.dish")
    List<Dish> findAll();

    @Insert("INSERT INTO kitchen.dish(dish_id, balance, short_name, dish_composition) " +
            "VALUES (#{dishId}, #{balance}, #{shortName}, #{dishComposition})")
    @Options(useGeneratedKeys = true, keyProperty = "dishId")
    void insert(Dish dish);

    @Update("UPDATE kitchen.dish SET balance=#{balance}, short_name=#{shortName}, dish_composition=#{dishComposition} " +
            "WHERE dish_id=#{dishId}")
    void update(Dish dish);

    @Delete("DELETE FROM kitchen.dish WHERE dish_id=#{id}")
    void delete(Long id);
}

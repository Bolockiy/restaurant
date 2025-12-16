package liga.restaurant.kitchen.mapper;

import org.apache.ibatis.annotations.*;
import liga.restaurant.kitchen.entity.Dish;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface DishMapper {

    @Select("SELECT dish_id, balance, short_name, dish_composition FROM dish WHERE dish_id = #{id}")
    @Results({
            @Result(property = "dishId", column = "dish_id"),
            @Result(property = "balance", column = "balance"),
            @Result(property = "name", column = "short_name"),
            @Result(property = "dishComposition", column = "dish_composition")
    })
    Dish findById(Long id);

    @Select("SELECT dish_id, balance, short_name, dish_composition FROM dish")
    List<Dish> findAll();

    @Insert("INSERT INTO dish(dish_id, balance, short_name, dish_composition) " +
            "VALUES (#{dishId}, #{balance}, #{shortName}, #{dishComposition})")
    @Options(useGeneratedKeys = true, keyProperty = "dishId")
    void insert(Dish dish);

    @Update("UPDATE dish SET balance=#{balance}, short_name=#{shortName}, dish_composition=#{dishComposition} " +
            "WHERE dish_id=#{dishId}")
    void update(Dish dish);

    @Delete("DELETE FROM dish WHERE dish_id=#{id}")
    void delete(Long id);

    @Select("""
        SELECT dish_id, balance, short_name, dish_composition
        FROM dish
        WHERE dish_id = #{id}
        FOR UPDATE
    """)
    @Results({
            @Result(property = "dishId", column = "dish_id"),
            @Result(property = "balance", column = "balance"),
            @Result(property = "name", column = "short_name"),
            @Result(property = "dishComposition", column = "dish_composition")
    })
    Dish findByIdForUpdate(Long id);

    @Update("""
        UPDATE dish
        SET balance = balance - #{amount}
        WHERE dish_id = #{dishId}
    """)
    void decreaseBalance(@Param("dishId") Long dishId,
                         @Param("amount") Long amount);
}

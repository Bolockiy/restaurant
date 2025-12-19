package liga.restaurant.kitchen.mapper;

import liga.restaurant.kitchen.entity.OrderToDish;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderToDishMapper {

    // Получить все блюда по заказу
    @Select("""
                SELECT 
                    kitchen_order_id AS kitchenOrderId,
                    dish_id AS dishId,
                    dishes_number AS dishesNumber
                FROM order_to_dish
                WHERE kitchen_order_id = #{orderId}
            """)
    List<OrderToDish> findByOrderId(Long orderId);

    // Получить конкретную запись
    @Select("""
                SELECT 
                    kitchen_order_id AS kitchenOrderId,
                    dish_id AS dishId,
                    dishes_number AS dishesNumber
                FROM order_to_dish
                WHERE kitchen_order_id = #{kitchenOrderId}
                  AND dish_id = #{dishId}
            """)
    OrderToDish findOne(@Param("kitchenOrderId") Long kitchenOrderId,
                        @Param("dishId") Long dishId);

    // Вставка
    @Insert("""
                INSERT INTO order_to_dish(kitchen_order_id, dish_id, dishes_number)
                VALUES (#{kitchenOrderId}, #{dishId}, #{dishesNumber})
            """)
    void insert(OrderToDish orderToDish);

    // Обновление количества
    @Update("""
                UPDATE order_to_dish 
                SET dishes_number = #{dishesNumber}
                WHERE kitchen_order_id = #{kitchenOrderId}
                  AND dish_id = #{dishId}
            """)
    void update(OrderToDish orderToDish);

    // Удаление одного блюда из заказа
    @Delete("""
                DELETE FROM order_to_dish
                WHERE kitchen_order_id = #{kitchenOrderId}
                  AND dish_id = #{dishId}
            """)
    void delete(@Param("kitchenOrderId") Long kitchenOrderId,
                @Param("dishId") Long dishId);

    // Удалить все блюда из заказа
    @Delete("""
                DELETE FROM order_to_dish
                WHERE kitchen_order_id = #{kitchenOrderId}
            """)
    void deleteByOrderId(Long kitchenOrderId);
}

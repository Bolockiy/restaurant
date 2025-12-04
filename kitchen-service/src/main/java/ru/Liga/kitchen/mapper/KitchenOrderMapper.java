package ru.Liga.kitchen.mapper;

import org.apache.ibatis.annotations.*;
import ru.Liga.kitchen.entity.KitchenOrder;
import java.util.List;

@Mapper
public interface KitchenOrderMapper {

    @Select("SELECT kitchen_order_id, waiter_order_no, status, create_dttm FROM kitchen.kitchen_order WHERE kitchen_order_id = #{id}")
    KitchenOrder findById(Long id);

    @Select("SELECT kitchen_order_id, waiter_order_no, status, create_dttm FROM kitchen.kitchen_order")
    List<KitchenOrder> findAll();

    @Insert("INSERT INTO kitchen.kitchen_order(kitchen_order_id, waiter_order_no, status, create_dttm) " +
            "VALUES (#{kitchenOrderId}, #{waiterOrderNo}, #{status}, #{createDttm})")
    @Options(useGeneratedKeys = true, keyProperty = "kitchenOrderId")
    void insert(KitchenOrder kitchenOrder);

    @Update("UPDATE kitchen.kitchen_order SET waiter_order_no=#{waiterOrderNo}, status=#{status}, create_dttm=#{createDttm} " +
            "WHERE kitchen_order_id=#{kitchenOrderId}")
    void update(KitchenOrder kitchenOrder);

    @Delete("DELETE FROM kitchen.kitchen_order WHERE kitchen_order_id=#{id}")
    void delete(Long id);
}

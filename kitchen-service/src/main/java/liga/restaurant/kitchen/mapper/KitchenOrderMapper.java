package liga.restaurant.kitchen.mapper;

import org.apache.ibatis.annotations.*;
import liga.restaurant.kitchen.entity.KitchenOrder;
import java.util.List;

@Mapper
public interface KitchenOrderMapper {

    @Select("""
        SELECT 
            kitchen_order_id AS kitchenOrderId,
            waiter_order_no  AS waiterOrderNo,
            status,
            create_dttm      AS createDttm
        FROM kitchen_order
        WHERE kitchen_order_id = #{id}
    """)
    KitchenOrder findById(Long id);

    @Select("""
        SELECT 
            kitchen_order_id AS kitchenOrderId,
            waiter_order_no  AS waiterOrderNo,
            status,
            create_dttm      AS createDttm
        FROM kitchen_order
    """)
    List<KitchenOrder> findAll();

    @Insert("""
        INSERT INTO kitchen_order(waiter_order_no, status, create_dttm)
        VALUES (#{waiterOrderNo}, #{status}, #{createDttm})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "kitchenOrderId")
    void insert(KitchenOrder kitchenOrder);

    @Update("""
        UPDATE kitchen_order 
        SET waiter_order_no=#{waiterOrderNo}, 
            status=#{status}, 
            create_dttm=#{createDttm}
        WHERE kitchen_order_id=#{kitchenOrderId}
    """)
    void update(KitchenOrder kitchenOrder);

    @Delete("DELETE FROM kitchen_order WHERE kitchen_order_id=#{id}")
    void delete(Long id);
}
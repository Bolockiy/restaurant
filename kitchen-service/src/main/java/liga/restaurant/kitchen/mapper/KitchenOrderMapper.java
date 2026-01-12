package liga.restaurant.kitchen.mapper;

import liga.restaurant.kitchen.entity.KitchenOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface KitchenOrderMapper {

    KitchenOrder findById(Long id);

    List<KitchenOrder> findAll(
            @Param("limit") int limit,
            @Param("offset") int offset
    );

    void insert(KitchenOrder kitchenOrder);

    void update(KitchenOrder kitchenOrder);

    void delete(Long id);
}
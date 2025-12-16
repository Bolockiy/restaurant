package liga.restaurant.kitchen.mapper;

import org.apache.ibatis.annotations.*;
import liga.restaurant.kitchen.entity.KitchenOrder;
import java.util.List;

@Mapper
public interface KitchenOrderMapper {

    KitchenOrder findById(Long id);

    List<KitchenOrder> findAll();

    void insert(KitchenOrder kitchenOrder);

    void update(KitchenOrder kitchenOrder);

    void delete(Long id);
}
package liga.restaurant.waiter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import liga.restaurant.waiter.entity.WaiterOrder;

public interface WaiterOrderRepository extends JpaRepository<WaiterOrder, Long> {
}

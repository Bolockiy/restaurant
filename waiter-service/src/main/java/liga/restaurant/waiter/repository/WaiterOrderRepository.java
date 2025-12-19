package liga.restaurant.waiter.repository;

import liga.restaurant.waiter.entity.WaiterOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaiterOrderRepository extends JpaRepository<WaiterOrder, Long> {
}

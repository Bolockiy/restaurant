package liga.restaurant.waiter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import liga.restaurant.waiter.entity.OrderPosition;

public interface OrderPositionRepository extends JpaRepository<OrderPosition, Long> {
}

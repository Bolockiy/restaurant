package liga.restaurant.waiter.repository;

import liga.restaurant.waiter.entity.OrderPosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderPositionRepository extends JpaRepository<OrderPosition, Long> {
}

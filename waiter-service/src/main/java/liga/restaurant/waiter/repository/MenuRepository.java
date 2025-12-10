package liga.restaurant.waiter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import liga.restaurant.waiter.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}

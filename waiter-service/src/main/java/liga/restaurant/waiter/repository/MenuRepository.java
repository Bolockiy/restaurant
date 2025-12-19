package liga.restaurant.waiter.repository;

import liga.restaurant.waiter.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}

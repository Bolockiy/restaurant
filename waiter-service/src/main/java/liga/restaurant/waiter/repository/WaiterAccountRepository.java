package liga.restaurant.waiter.repository;

import liga.restaurant.waiter.entity.WaiterAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WaiterAccountRepository extends JpaRepository<WaiterAccount, Long> {
    Optional<WaiterAccount> findByName(String name);
}


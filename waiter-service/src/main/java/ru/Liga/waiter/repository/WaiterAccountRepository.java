package ru.Liga.waiter.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.Liga.waiter.entity.WaiterAccount;

public interface WaiterAccountRepository extends JpaRepository<WaiterAccount, Long> {
    Optional<WaiterAccount> findByName(String name);
}


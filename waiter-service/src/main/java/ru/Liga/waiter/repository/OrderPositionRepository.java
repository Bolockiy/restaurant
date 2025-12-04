package ru.Liga.waiter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.Liga.waiter.entity.OrderPosition;

public interface OrderPositionRepository extends JpaRepository<OrderPosition, Long> {
}

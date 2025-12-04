package ru.Liga.waiter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.Liga.waiter.entity.WaiterOrder;

public interface WaiterOrderRepository extends JpaRepository<WaiterOrder, Long> {
}

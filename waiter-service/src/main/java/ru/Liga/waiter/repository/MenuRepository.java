package ru.Liga.waiter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.Liga.waiter.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}

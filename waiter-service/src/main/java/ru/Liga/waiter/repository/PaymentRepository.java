package ru.Liga.waiter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.Liga.waiter.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}

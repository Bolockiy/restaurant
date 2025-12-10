package liga.restaurant.waiter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import liga.restaurant.waiter.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}

package liga.restaurant.waiter.repository;

import liga.restaurant.waiter.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}

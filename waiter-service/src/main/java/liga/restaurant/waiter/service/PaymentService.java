package liga.restaurant.waiter.service;

import liga.restaurant.waiter.entity.Payment;
import liga.restaurant.waiter.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository repo;

    public Payment save(Payment payment) {
        Payment saved = repo.save(payment);
        log.debug("Payment saved: {}", saved);
        return saved;
    }

    public List<Payment> findAll() {
        log.info("Fetching all payments");
        List<Payment> payments = repo.findAll();
        log.debug("Found {} payments", payments.size());
        return payments;
    }

    public Payment findById(Long id) {
        log.info("Fetching payment by id: {}", id);
        return repo.findById(id)
                .map(payment -> {
                    log.debug("Found payment: {}", payment);
                    return payment;
                })
                .orElseGet(() -> {
                    log.warn("Payment not found: id={}", id);
                    return null;
                });
    }

    public void delete(Long id) {
        log.info("Deleting payment: id={}", id);
        if (!repo.existsById(id)) {
            log.warn("Payment not found for deletion: id={}", id);
            return;
        }
        repo.deleteById(id);
        log.info("Payment deleted: id={}", id);
    }

}
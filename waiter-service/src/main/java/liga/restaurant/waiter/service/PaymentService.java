package liga.restaurant.waiter.service;

import org.springframework.stereotype.Service;
import liga.restaurant.waiter.entity.Payment;
import liga.restaurant.waiter.repository.PaymentRepository;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository repo;

    public PaymentService(PaymentRepository repo) {
        this.repo = repo;
    }

    public Payment save(Payment payment) {
        return repo.save(payment);
    }

    public List<Payment> findAll() {
        return repo.findAll();
    }

    public Payment findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}

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
        log.debug("Платёж сохранён: {}", saved);
        return saved;
    }

    public List<Payment> findAll() {
        log.info("Получение всех платежей");
        List<Payment> payments = repo.findAll();
        log.debug("Найдено платежей: {}", payments.size());
        return payments;
    }

    public Payment findById(Long id) {
        log.info("Получение платежа по id={}", id);
        return repo.findById(id)
                .map(payment -> {
                    log.debug("Платёж найден: {}", payment);
                    return payment;
                })
                .orElseGet(() -> {
                    log.warn("Платёж не найден: id={}", id);
                    return null;
                });
    }

    public void delete(Long id) {
        log.info("Удаление платежа: id={}", id);
        if (!repo.existsById(id)) {
            log.warn("Платёж не найден для удаления: id={}", id);
            return;
        }
        repo.deleteById(id);
        log.info("Платёж удалён: id={}", id);
    }
}
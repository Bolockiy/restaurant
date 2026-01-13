package liga.restaurant.waiter.service;

import liga.restaurant.waiter.entity.OrderPosition;
import liga.restaurant.waiter.repository.OrderPositionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderPositionService {

    private final OrderPositionRepository repo;

    public OrderPosition save(OrderPosition position) {
        OrderPosition saved = repo.save(position);
        log.debug("Позиция заказа сохранена: {}", saved);
        return saved;
    }

    public List<OrderPosition> findAll() {
        log.info("Получение всех позиций заказа");
        List<OrderPosition> positions = repo.findAll();
        log.debug("Найдено позиций заказа: {}", positions.size());
        return positions;
    }

    public OrderPosition findById(Long id) {
        log.info("Получение позиции заказа по id={}", id);
        return repo.findById(id)
                .map(pos -> {
                    log.debug("Позиция заказа найдена: {}", pos);
                    return pos;
                })
                .orElseGet(() -> {
                    log.warn("Позиция заказа не найдена: id={}", id);
                    return null;
                });
    }

    public void delete(Long id) {
        log.info("Удаление позиции заказа: id={}", id);
        if (!repo.existsById(id)) {
            log.warn("Позиция заказа не найдена для удаления: id={}", id);
            return;
        }
        repo.deleteById(id);
        log.info("Позиция заказа удалена: id={}", id);
    }
}
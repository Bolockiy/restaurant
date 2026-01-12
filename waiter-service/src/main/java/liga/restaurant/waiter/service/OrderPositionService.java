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
        log.debug("Order position saved: {}", saved);
        return saved;
    }

    public List<OrderPosition> findAll() {
        log.info("Fetching all order positions");
        List<OrderPosition> positions = repo.findAll();
        log.debug("Found {} order positions", positions.size());
        return positions;
    }

    public OrderPosition findById(Long id) {
        log.info("Fetching order position by id: {}", id);
        return repo.findById(id)
                .map(pos -> {
                    log.debug("Found order position: {}", pos);
                    return pos;
                })
                .orElseGet(() -> {
                    log.warn("Order position not found: id={}", id);
                    return null;
                });
    }

    public void delete(Long id) {
        log.info("Deleting order position: id={}", id);
        if (!repo.existsById(id)) {
            log.warn("Order position not found for deletion: id={}", id);
            return;
        }
        repo.deleteById(id);
        log.info("Order position deleted: id={}", id);
    }

}
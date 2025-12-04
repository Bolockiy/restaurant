package ru.Liga.waiter.service;

import org.springframework.stereotype.Service;
import ru.Liga.waiter.entity.OrderPosition;
import ru.Liga.waiter.repository.OrderPositionRepository;

import java.util.List;

@Service
public class OrderPositionService {

    private final OrderPositionRepository repo;

    public OrderPositionService(OrderPositionRepository repo) {
        this.repo = repo;
    }

    public OrderPosition save(OrderPosition position) {
        return repo.save(position);
    }

    public List<OrderPosition> findAll() {
        return repo.findAll();
    }

    public OrderPosition findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}

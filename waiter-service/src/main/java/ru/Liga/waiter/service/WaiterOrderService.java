package ru.Liga.waiter.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.Liga.waiter.entity.OrderPosition;
import ru.Liga.waiter.entity.WaiterOrder;
import ru.Liga.waiter.repository.WaiterOrderRepository;

import java.util.List;

@Service
public class WaiterOrderService {

    private final WaiterOrderRepository repo;

    public WaiterOrderService(WaiterOrderRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public WaiterOrder createOrder(WaiterOrder order) {
        for (OrderPosition pos : order.getPositions()) {
            pos.setOrder(order);
        }
        return repo.save(order);
    }

    public List<WaiterOrder> findAll() {
        return repo.findAll();
    }

    public WaiterOrder findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

}

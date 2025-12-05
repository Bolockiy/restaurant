package ru.Liga.waiter.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.Liga.dto.KitchenOrderRequestDto;
import ru.Liga.dto.WaiterOrderDto;
import ru.Liga.waiter.entity.OrderPosition;
import ru.Liga.waiter.entity.WaiterOrder;
import ru.Liga.waiter.feign.KitchenFeignClient;
import ru.Liga.waiter.repository.WaiterOrderRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WaiterOrderService {

    private final WaiterOrderRepository repo;
    private final KitchenFeignClient kitchenClient;
    public WaiterOrderService(WaiterOrderRepository repo, KitchenFeignClient kitchenClient) {
        this.repo = repo;
        this.kitchenClient = kitchenClient;
    }

    public WaiterOrder createOrder(WaiterOrder dto)
    {
       return repo.save(dto);
    }

    public void createOrderKitchen(@RequestBody KitchenOrderRequestDto dto) {
        kitchenClient.sendOrderToKitchen(dto);
    }
    public List<WaiterOrderDto> findAll() {
        return repo.findAll().stream()
                .map(o -> new WaiterOrderDto(
                        o.getId(),
                        o.getStatus(),
                        o.getTableNo(),
                        o.getCreateDttm()
                ))
                .collect(Collectors.toList());
    }

    public WaiterOrderDto findById(Long id) {
        return repo.findById(id)
                .map(o -> new WaiterOrderDto(
                        o.getId(),
                        o.getStatus(),
                        o.getTableNo(),
                        o.getCreateDttm()
                ))
                .orElse(null);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Transactional
    public void updateOrderStatus(Long waiterOrderNo, String status) {

        WaiterOrder order = repo.findById(waiterOrderNo)
                .orElseThrow(() -> new RuntimeException("Order not found: " + waiterOrderNo));

        order.setStatus(status);
        repo.save(order);
    }

}

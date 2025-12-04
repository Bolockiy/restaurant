package ru.Liga.kitchen.service;

import org.springframework.stereotype.Service;
import ru.Liga.dto.KitchenOrderRequestDto;
import ru.Liga.dto.OrderStatusDto;
import ru.Liga.kitchen.entity.KitchenOrder;
import ru.Liga.kitchen.feign.WaiterFeignClient;
import ru.Liga.kitchen.mapper.KitchenOrderMapper;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class KitchenService {

    private final KitchenOrderMapper kitchenOrderMapper;
    private final WaiterFeignClient waiterClient;
    public KitchenService(KitchenOrderMapper kitchenOrderMapper, WaiterFeignClient waiterClient) {
        this.kitchenOrderMapper = kitchenOrderMapper;
        this.waiterClient = waiterClient;
    }
    public void setOrderReady(KitchenOrderRequestDto dto) {
        KitchenOrder order = new KitchenOrder();
        order.setWaiterOrderNo(dto.getWaiterOrderNo());
        order.setStatus("NEW");
        order.setCreateDttm(OffsetDateTime.now());

        kitchenOrderMapper.insert(order);

    }
    public KitchenOrder getById(Long id) {
        return kitchenOrderMapper.findById(id);
    }

    public List<KitchenOrder> getAll() {
        return kitchenOrderMapper.findAll();
    }

    public void create(KitchenOrder kitchenOrder) {
        kitchenOrderMapper.insert(kitchenOrder);
    }

    public void update(KitchenOrder kitchenOrder) {
        kitchenOrderMapper.update(kitchenOrder);
    }

    public void delete(Long id) {
        kitchenOrderMapper.delete(id);
    }
}

package ru.Liga.kitchen.service;

import org.springframework.stereotype.Service;
import ru.Liga.kitchen.entity.KitchenOrder;
import ru.Liga.kitchen.mapper.KitchenOrderMapper;

import java.util.List;

@Service
public class KitchenService {

    private final KitchenOrderMapper kitchenOrderMapper;

    public KitchenService(KitchenOrderMapper kitchenOrderMapper) {
        this.kitchenOrderMapper = kitchenOrderMapper;
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

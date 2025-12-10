package liga.restaurant.kitchen.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import liga.restaurant.dto.KitchenOrderRequestDto;
import liga.restaurant.dto.OrderStatusDto;
import liga.restaurant.dto.OrderToDishDto;
import liga.restaurant.kitchen.Kafka.KitchenKafkaProducer;
import liga.restaurant.kitchen.entity.Dish;
import liga.restaurant.kitchen.entity.KitchenOrder;
import liga.restaurant.kitchen.entity.OrderToDish;
import liga.restaurant.kitchen.exception.NotFoundException;
import liga.restaurant.kitchen.exception.BusinessException;
import liga.restaurant.kitchen.mapper.KitchenOrderMapper;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KitchenService {

    private final KitchenOrderMapper kitchenOrderMapper;
    private final OrderToDishService orderToDishService;
    private final KitchenKafkaProducer kitchenKafkaProducer;
    private final DishService dishService;

    public boolean checkProductsAvailability(KitchenOrderRequestDto dto) {
        for (OrderToDishDto dish : dto.getDishes()) {
            Dish d =  dishService.getById(dish.getDishId());
            if (d.getBalance() < dish.getDishesNumber()) {
                return false;
            }
        }
        return true;
    }
    @Transactional
    @Valid
    public boolean processOrderFromWaiter(KitchenOrderRequestDto dto) {
      //  System.out.println("Processing order: " + dto.getWaiterOrderNo());
        if (!checkProductsAvailability(dto))
            return false;
        KitchenOrder order = new KitchenOrder();
        order.setWaiterOrderNo(dto.getWaiterOrderNo());
        order.setStatus("RECEIVED");
        order.setCreateDttm(OffsetDateTime.now());

        kitchenOrderMapper.insert(order);
        Long kitchenOrderId = order.getKitchenOrderId();

        for (OrderToDishDto d : dto.getDishes()) {
            OrderToDish otd = new OrderToDish();
            otd.setKitchenOrderId(kitchenOrderId);
            otd.setDishId(d.getDishId());
            otd.setDishesNumber(d.getDishesNumber());

            orderToDishService.create(otd);
        }
        //System.out.println("Order saved to kitchen DB");
        return true;
    }


    @Transactional
    @Valid
    public void markOrderReady(Long kitchenOrderId) {
        KitchenOrder order = kitchenOrderMapper.findById(kitchenOrderId);
        if ("READY".equalsIgnoreCase(order.getStatus())) {
            throw new BusinessException("Order is already READY");
        }
        order.setStatus("READY");
        kitchenOrderMapper.update(order);
        OrderStatusDto dto = new OrderStatusDto(order.getWaiterOrderNo(), "READY");
        kitchenKafkaProducer.sendStatusToWaiter(dto);
    }

    @Valid
    public KitchenOrder getById(Long id) {
        KitchenOrder order = kitchenOrderMapper.findById(id);
        return order;
    }


    public List<KitchenOrder> getAll() {
        return kitchenOrderMapper.findAll();
    }

    @Valid
    public void create(KitchenOrder kitchenOrder) {
        kitchenOrder.setStatus("NEW");
        kitchenOrder.setCreateDttm(OffsetDateTime.now());
        kitchenOrderMapper.insert(kitchenOrder);
    }
    @Valid
    public void update(KitchenOrder kitchenOrder) {
        kitchenOrderMapper.update(kitchenOrder);
    }

    @Valid
    public void delete(Long id) {
        kitchenOrderMapper.delete(id);
        orderToDishService.deleteByOrderId(id);
    }

}

package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import liga.restaurant.dto.KitchenOrderRequestDto;
import liga.restaurant.dto.WaiterOrderDto;
import liga.restaurant.waiter.entity.WaiterOrder;
import liga.restaurant.waiter.kafka.KitchenKafkaProducer;
import liga.restaurant.waiter.repository.WaiterOrderRepository;
import liga.restaurant.waiter.service.WaiterOrderService;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WaiterOrderServiceTest {

    @Mock
    private WaiterOrderRepository repo;

    @Mock
    private KitchenKafkaProducer kafkaProducer;

    @InjectMocks
    private WaiterOrderService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_shouldSaveOrder() {
        WaiterOrder order = new WaiterOrder();
        order.setTableNo("A1");
        order.setStatus("NEW");

        when(repo.save(order)).thenReturn(order);

        WaiterOrder result = service.createOrder(order);

        assertEquals("A1", result.getTableNo());
        verify(repo, times(1)).save(order);
    }

    @Test
    void createOrderKitchen_shouldSendKafkaMessage() {
        KitchenOrderRequestDto dto = new KitchenOrderRequestDto();
        dto.setWaiterOrderNo(1L);

        service.createOrderKitchen(dto);

        verify(kafkaProducer, times(1)).sendOrderToKitchen(dto);
    }

    @Test
    void findAll_shouldReturnDtoList() {
        WaiterOrder o1 = new WaiterOrder();
        o1.setId(1L); o1.setStatus("NEW"); o1.setTableNo("A1"); o1.setCreateDttm(OffsetDateTime.now());

        WaiterOrder o2 = new WaiterOrder();
        o2.setId(2L); o2.setStatus("READY"); o2.setTableNo("B2"); o2.setCreateDttm(OffsetDateTime.now());

        when(repo.findAll()).thenReturn(Arrays.asList(o1, o2));

        List<WaiterOrderDto> result = service.findAll();
        assertEquals(2, result.size());
    }

    @Test
    void findById_existingOrder_shouldReturnDto() {
        WaiterOrder o = new WaiterOrder();
        o.setId(1L); o.setStatus("NEW"); o.setTableNo("A1"); o.setCreateDttm(OffsetDateTime.now());

        when(repo.findById(1L)).thenReturn(Optional.of(o));

        WaiterOrderDto dto = service.findById(1L);

        assertEquals(1L, dto.getId());
        assertEquals("A1", dto.getTableNo());
    }

    @Test
    void findById_nonExistingOrder_shouldThrow() {
        when(repo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.findById(1L));
    }

    @Test
    void updateOrderStatus_shouldChangeStatus() {
        WaiterOrder order = new WaiterOrder();
        order.setId(1L);
        order.setStatus("NEW");

        when(repo.findById(1L)).thenReturn(Optional.of(order));
        when(repo.save(order)).thenReturn(order);

        service.updateOrderStatus(1L, "READY");

        assertEquals("READY", order.getStatus());
        verify(repo).save(order);
    }

    @Test
    void updateOrderStatus_sameStatus_shouldNotSave() {
        WaiterOrder order = new WaiterOrder();
        order.setId(1L);
        order.setStatus("NEW");

        when(repo.findById(1L)).thenReturn(Optional.of(order));

        service.updateOrderStatus(1L, "NEW");

        verify(repo, never()).save(any());
    }
}

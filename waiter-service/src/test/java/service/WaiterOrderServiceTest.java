package service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.Liga.waiter.entity.WaiterOrder;
import ru.Liga.waiter.repository.WaiterOrderRepository;
import ru.Liga.waiter.service.WaiterOrderService;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WaiterOrderServiceTest {

    @Mock
    private WaiterOrderRepository repository;

    @InjectMocks
    private WaiterOrderService service;

    @Test
    void createOrder_success() {
        WaiterOrder input = new WaiterOrder();
        input.setTableNo("5");

        WaiterOrder saved = new WaiterOrder();
        saved.setId(10L);

        when(repository.save(input)).thenReturn(saved);

        WaiterOrder result = service.createOrder(input);

        assertEquals(10L, result.getId());
        Mockito.verify(repository).save(input);
    }
}

package controllers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import ru.Liga.kitchen.entity.KitchenOrder;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = ru.Liga.kitchen.KitchenApplication.class)
class KitchenControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createOrder_shouldReturnCreatedOrder() {
        KitchenOrder order = new KitchenOrder();
        order.setWaiterOrderNo(1L);
        order.setStatus("NEW");
        order.setCreateDttm(OffsetDateTime.now());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<KitchenOrder> request = new HttpEntity<>(order, headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(
                "/kitchen/orders", request, Void.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}

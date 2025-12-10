package controllers;

import liga.restaurant.waiter.WaiterApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import liga.restaurant.dto.WaiterAccountDto;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = WaiterApplication.class)

class WaiterAccountControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createAccount_shouldReturnCreatedAccount() {
        WaiterAccountDto dto = new WaiterAccountDto();
        dto.setName("Иван");
        dto.setSex("M");
        dto.setEmploymentDate(OffsetDateTime.now());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<WaiterAccountDto> request = new HttpEntity<>(dto, headers);

        ResponseEntity<WaiterAccountDto> response = restTemplate.postForEntity(
                "/waiter/accounts", request, WaiterAccountDto.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Иван");
    }
}
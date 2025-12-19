package liga.restaurant.waiter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {
        "liga.restaurant"
})
public class WaiterApplication {
    public static void main(String[] args) {
        SpringApplication.run(WaiterApplication.class, args);
    }
}

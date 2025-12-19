package liga.restaurant.waiter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "liga.restaurant"
})
public class WaiterApplication {
    public static void main(String[] args) {
        SpringApplication.run(WaiterApplication.class, args);
    }
}

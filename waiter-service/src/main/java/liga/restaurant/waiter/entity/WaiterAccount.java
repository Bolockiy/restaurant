package liga.restaurant.waiter.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "waiter_account")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WaiterAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "waiter_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String sex;

    @Column(name = "employment_date", nullable = false)
    private OffsetDateTime employmentDate;
}

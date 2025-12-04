package ru.Liga.waiter.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "waiter_account", schema = "waiter")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WaiterAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "waiter_id_seq")
    @SequenceGenerator(
            name = "waiter_id_seq",
            sequenceName = "waiter.waiter_account_waiter_id_seq",
            allocationSize = 1
    )
    @Column(name = "waiter_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String sex;

    @Column(name = "employment_date", nullable = false)
    private OffsetDateTime employmentDate;

    @OneToMany(mappedBy = "waiter", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<WaiterOrder> orders;
}

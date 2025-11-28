package ru.Liga.waiter.entity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "waiter_account", schema = "waiter")
public class WaiterAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "waiter_id")
    private Long id;

    private String name;

    @Column(name = "employment_date")
    private OffsetDateTime employmentDate;

    private String sex;
}

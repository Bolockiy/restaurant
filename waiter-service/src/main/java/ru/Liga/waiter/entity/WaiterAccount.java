package ru.Liga.waiter.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Сущность аккаунта официанта")
public class WaiterAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "waiter_id_seq")
    @SequenceGenerator(
            name = "waiter_id_seq",
            sequenceName = "waiter.waiter_account_waiter_id_seq",
            allocationSize = 1
    )
    @Column(name = "waiter_id")
    @Schema(description = "Уникальный идентификатор официанта", example = "12")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Имя официанта", example = "Иван Петров")
    private String name;

    @Column(nullable = false)
    @Schema(description = "Пол официанта", example = "MALE")
    private String sex;

    @Column(name = "employment_date", nullable = false)
    @Schema(
            description = "Дата и время трудоустройства официанта",
            example = "2024-01-15T10:30:00+03:00"
    )
    private OffsetDateTime employmentDate;

    @OneToMany(mappedBy = "waiter", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Schema(description = "Список заказов, оформленных этим официантом")
    private List<WaiterOrder> orders;
}

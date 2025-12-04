package ru.Liga.dto;

import lombok.*;

import java.time.OffsetDateTime;
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaiterOrderDto {
    Long id;
    String status;
    String tableNo;
    OffsetDateTime createDttm;
}

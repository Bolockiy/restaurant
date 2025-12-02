package ru.Liga.dto;

import lombok.*;

import java.time.OffsetDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;



@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaiterAccountDto {

    private Long id;
    private String name;
    private String sex;
    private OffsetDateTime employmentDate;

    public WaiterAccountDto(String name, String sex, OffsetDateTime employmentDate) {
        this.name = name;
        this.sex = sex;
        this.employmentDate = employmentDate;
    }

}

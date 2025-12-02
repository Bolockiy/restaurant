package ru.Liga.dto;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishDto {
    private Long id;
    private Long balance;
    private String shortName;
    private String composition;
}

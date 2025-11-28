package ru.Liga.waiter.mapper;


import ru.Liga.dto.WaiterAccountDto;

import org.mapstruct.Mapper;
import ru.Liga.waiter.entity.WaiterAccount;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WaiterAccountMapper {

    WaiterAccountDto toDto(WaiterAccount entity);
    List<WaiterAccountDto> toDto(List<WaiterAccount> entities);
    WaiterAccount toEntity(WaiterAccountDto dto);
}

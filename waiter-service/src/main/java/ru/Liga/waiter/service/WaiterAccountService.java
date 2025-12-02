package ru.Liga.waiter.service;

import org.springframework.stereotype.Service;
import ru.Liga.dto.WaiterAccountDto;
import ru.Liga.waiter.entity.WaiterAccount;
import ru.Liga.waiter.repository.WaiterAccountRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WaiterAccountService {

    private final WaiterAccountRepository repo;

    public WaiterAccountService(WaiterAccountRepository repo) {
        this.repo = repo;
    }

    // --- Конвертация DTO -> Entity ---
    private WaiterAccount toEntity(WaiterAccountDto dto) {
        WaiterAccount entity = new WaiterAccount();
        entity.setName(dto.getName());
        entity.setSex(dto.getSex());
        entity.setEmploymentDate(dto.getEmploymentDate());
        return entity;
    }

    // --- Конвертация Entity -> DTO ---
    private WaiterAccountDto toDto(WaiterAccount entity) {
        return new WaiterAccountDto(
                entity.getId(),
                entity.getName(),
                entity.getSex(),
                entity.getEmploymentDate()
        );
    }

    // Создание нового аккаунта
    public WaiterAccountDto save(WaiterAccountDto dto) {
        WaiterAccount entity = toEntity(dto);
        WaiterAccount saved = repo.save(entity);  // id генерируется автоматически
        return toDto(saved);
    }

    // Получение всех аккаунтов
    public List<WaiterAccountDto> findAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    public List<WaiterAccountDto> findByName(String name) {
        return repo.findByName(name).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}

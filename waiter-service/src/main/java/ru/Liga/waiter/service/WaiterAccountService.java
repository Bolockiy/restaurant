package ru.Liga.waiter.service;

import org.springframework.stereotype.Service;
import ru.Liga.dto.WaiterAccountDto;
import ru.Liga.waiter.entity.WaiterAccount;
import ru.Liga.waiter.repository.WaiterAccountRepository;
import ru.Liga.waiter.mapper.GenericMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WaiterAccountService {

    private final WaiterAccountRepository repo;
    private final GenericMapper<WaiterAccount, WaiterAccountDto> mapper;

    public WaiterAccountService(WaiterAccountRepository repo) {
        this.repo = repo;
        this.mapper = new GenericMapper<>(
                this::toDto,
                this::toEntity
        );
    }

    private WaiterAccount toEntity(WaiterAccountDto dto) {
        WaiterAccount entity = new WaiterAccount();
        entity.setName(dto.getName());
        entity.setSex(dto.getSex());
        entity.setEmploymentDate(dto.getEmploymentDate());
        return entity;
    }

    private WaiterAccountDto toDto(WaiterAccount entity) {
        return new WaiterAccountDto(
                entity.getId(),
                entity.getName(),
                entity.getSex(),
                entity.getEmploymentDate()
        );
    }

    // CREATE
    public WaiterAccountDto save(WaiterAccountDto dto) {
        WaiterAccount saved = repo.save(mapper.toEntity(dto));
        return mapper.toDto(saved);
    }

    // READ ALL
    public List<WaiterAccountDto> findAll() {
        return mapper.toDtoList(repo.findAll());
    }

    // READ BY NAME
    public List<WaiterAccountDto> findByName(String name) {
        return repo.findByName(name).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // READ ONE
    public WaiterAccountDto findById(Long id) {
        return repo.findById(id)
                .map(mapper::toDto)
                .orElse(null);
    }

    // UPDATE
    public WaiterAccountDto update(Long id, WaiterAccountDto dto) {
        return repo.findById(id)
                .map(existing -> {
                    existing.setName(dto.getName());
                    existing.setSex(dto.getSex());
                    existing.setEmploymentDate(dto.getEmploymentDate());
                    return mapper.toDto(repo.save(existing));
                })
                .orElse(null);
    }

    // DELETE
    public boolean delete(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
}
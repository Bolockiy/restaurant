package ru.Liga.waiter.service;

import org.springframework.stereotype.Service;
import ru.Liga.dto.WaiterAccountDto;
import ru.Liga.waiter.entity.WaiterAccount;
import ru.Liga.waiter.repository.WaiterAccountRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WaiterAccountService {

    private final WaiterAccountRepository repo;

    public WaiterAccountService(WaiterAccountRepository repo) {
        this.repo = repo;
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

    public WaiterAccountDto save(WaiterAccountDto dto) {
        WaiterAccount saved = repo.save(toEntity(dto));
        return toDto(saved);
    }

    public List<WaiterAccountDto> findAll() {
        return repo.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<WaiterAccountDto> findByName(String name) {
        return repo.findByName(name)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public WaiterAccountDto findById(Long id) {
        return repo.findById(id)
                .map(this::toDto)
                .orElse(null);
    }

    public WaiterAccountDto update(Long id, WaiterAccountDto dto) {
        return repo.findById(id)
                .map(existing -> {
                    existing.setName(dto.getName());
                    existing.setSex(dto.getSex());
                    existing.setEmploymentDate(dto.getEmploymentDate());
                    return toDto(repo.save(existing));
                })
                .orElse(null);
    }

    public boolean delete(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
}

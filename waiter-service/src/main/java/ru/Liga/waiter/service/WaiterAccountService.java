package ru.Liga.waiter.service;
import org.springframework.stereotype.Service;
import ru.Liga.dto.WaiterAccountDto;
import ru.Liga.waiter.entity.WaiterAccount;
import ru.Liga.waiter.mapper.WaiterAccountMapper;
import ru.Liga.waiter.repository.WaiterAccountRepository;

import java.util.List;

@Service
public class WaiterAccountService {
    private final WaiterAccountRepository repo;
    private final WaiterAccountMapper mapper;

    public WaiterAccountService(WaiterAccountRepository repo, WaiterAccountMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public WaiterAccountDto save(WaiterAccountDto dto) {
        WaiterAccount entity = mapper.toEntity(dto);
        WaiterAccount saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    public WaiterAccountDto findOne(Long id) {
        return mapper.toDto(repo.findById(id).orElseThrow());
    }
    public List<WaiterAccountDto> findAll() {
        return mapper.toDto(repo.findAll());
    }
    public WaiterAccountDto findAccountByName(String name) {
        return mapper.toDto(repo.findByName(name).orElseThrow());
    }

    public WaiterAccountDto delete(String name) {
        WaiterAccount entity = repo.findByName(name).orElseThrow();
        repo.delete(entity);
        return mapper.toDto(entity);
    }
}

package liga.restaurant.waiter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import liga.restaurant.dto.WaiterAccountDto;
import liga.restaurant.waiter.entity.WaiterAccount;
import liga.restaurant.waiter.repository.WaiterAccountRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WaiterAccountService {
    private final WaiterAccountRepository repo;

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
        log.info("Saving new waiter account: name={}, sex={}, employmentDate={}", dto.getName(), dto.getSex(), dto.getEmploymentDate());
        WaiterAccount saved = repo.save(toEntity(dto));
        log.debug("Waiter account saved: {}", saved);
        return toDto(saved);
    }

    public List<WaiterAccountDto> findAll() {
        log.info("Fetching all waiter accounts");
        List<WaiterAccountDto> result = repo.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        log.debug("Found {} waiter accounts", result.size());
        return result;
    }

    public List<WaiterAccountDto> findByName(String name) {
        log.info("Searching waiter accounts by name: {}", name);
        List<WaiterAccountDto> result = repo.findByName(name)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        log.debug("Found {} accounts with name '{}'", result.size(), name);
        return result;
    }

    public WaiterAccountDto findById(Long id) {
        log.info("Fetching waiter account by id: {}", id);
        return repo.findById(id)
                .map(entity -> {
                    log.debug("Found waiter account: {}", entity);
                    return toDto(entity);
                })
                .orElseGet(() -> {
                    log.warn("Waiter account not found: id={}", id);
                    return null;
                });
    }

    public WaiterAccountDto update(Long id, WaiterAccountDto dto) {
        log.info("Updating waiter account: id={}", id);
        return repo.findById(id)
                .map(existing -> {
                    existing.setName(dto.getName());
                    existing.setSex(dto.getSex());
                    existing.setEmploymentDate(dto.getEmploymentDate());
                    WaiterAccount updated = repo.save(existing);
                    log.debug("Updated waiter account: {}", updated);
                    return toDto(updated);
                })
                .orElseGet(() -> {
                    log.warn("Waiter account not found for update: id={}", id);
                    return null;
                });
    }

    public boolean delete(Long id) {
        log.info("Deleting waiter account: id={}", id);
        if (!repo.existsById(id)) {
            log.warn("Waiter account not found for deletion: id={}", id);
            return false;
        }
        repo.deleteById(id);
        log.info("Waiter account deleted: id={}", id);
        return true;
    }
}

package liga.restaurant.waiter.service;

import liga.restaurant.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import liga.restaurant.dto.WaiterAccountDto;
import liga.restaurant.waiter.entity.WaiterAccount;
import liga.restaurant.waiter.repository.WaiterAccountRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class WaiterAccountService {

    public WaiterAccount save(WaiterAccount dto) {
        log.info("Saving new waiter account: name={}, sex={}, employmentDate={}", dto.getName(), dto.getSex(), dto.getEmploymentDate());
        return repo.save(dto);
    }

    public Page<WaiterAccountDto> findAll(Pageable pageable) {
        log.debug("Получение всех заказов с пагинацией: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());

        return repo.findAll(pageable).map(o -> new WaiterAccountDto(
                o.getId(),
                o.getName(),
                o.getSex(),
                o.getEmploymentDate()
        ));
    }

    public WaiterAccountDto findByName(String name) {
        log.info("Searching waiter accounts by name: {}", name);
        return repo.findByName(name).map(o -> new WaiterAccountDto(
                o.getId(),
                o.getName(),
                o.getSex(),
                o.getEmploymentDate()
                ))
                .orElseThrow(() -> new NotFoundException("Waiter account not found: " + name));
    }

    public WaiterAccountDto findById(Long id) {
        log.info("Fetching waiter account by id: {}", id);
        return repo.findById(id).map(o-> new WaiterAccountDto(
                o.getId(),
                o.getName(),
                o.getSex(),
                o.getEmploymentDate()
        )).orElseThrow(() -> new NotFoundException("Waiter account not found: " + id));
    }

    public WaiterAccount update(Long id, WaiterAccountDto dto) {
        log.info("Updating waiter account: id={}", id);
         WaiterAccount waiterAccount = repo.findById(id).orElseThrow(() -> new NotFoundException("Waiter account not found: " + id));
         waiterAccount.setName(dto.getName());
         waiterAccount.setSex(dto.getSex());
         waiterAccount.setEmploymentDate(dto.getEmploymentDate());
         repo.save(waiterAccount);
         return waiterAccount;
    }

    public void delete(Long id) {
        log.info("Deleting waiter account: id={}", id);
        if (!repo.existsById(id)) {
            log.warn("Waiter account not found for deletion: id={}", id);
            return;
        }
        repo.deleteById(id);
        log.info("Waiter account deleted: id={}", id);
    }

    private final WaiterAccountRepository repo;
}

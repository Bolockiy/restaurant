package liga.restaurant.waiter.service;

import liga.restaurant.NotFoundException;
import liga.restaurant.dto.WaiterAccountDto;
import liga.restaurant.waiter.entity.WaiterAccount;
import liga.restaurant.waiter.repository.WaiterAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WaiterAccountService {

    private final WaiterAccountRepository repo;

    public WaiterAccount save(WaiterAccount dto) {
        log.info("Создание нового аккаунта: name={}, sex={}, employmentDate={}", dto.getName(), dto.getSex(), dto.getEmploymentDate());
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
        log.info("Поиск аккаунта с именем: {}", name);
        return repo.findByName(name).map(o -> new WaiterAccountDto(
                        o.getId(),
                        o.getName(),
                        o.getSex(),
                        o.getEmploymentDate()
                ))
                .orElseThrow(() -> new NotFoundException("Официант не найден: " + name));
    }

    public WaiterAccountDto findById(Long id) {
        log.info("Получение аккаунта официанта по id={}", id);
        return repo.findById(id)
                .map(o -> new WaiterAccountDto(
                        o.getId(),
                        o.getName(),
                        o.getSex(),
                        o.getEmploymentDate()
                ))
                .orElseThrow(() ->
                        new NotFoundException("Официант не найден id: " + id)
                );
    }

    public WaiterAccount update(Long id, WaiterAccountDto dto) {
        log.info("Обновление аккаунта официанта: id={}", id);
        WaiterAccount waiterAccount = repo.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Официант не найден id: " + id)
                );

        waiterAccount.setName(dto.getName());
        waiterAccount.setSex(dto.getSex());
        waiterAccount.setEmploymentDate(dto.getEmploymentDate());

        repo.save(waiterAccount);
        return waiterAccount;
    }

    public void delete(Long id) {
        log.info("Удаление аккаунта: id={}", id);
        if (!repo.existsById(id)) {
            log.warn("Нет аккаунта: id={}", id);
            return;
        }
        repo.deleteById(id);
        log.info("Аккаунт удалён: id={}", id);
    }

}

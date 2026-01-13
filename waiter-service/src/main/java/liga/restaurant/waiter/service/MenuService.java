package liga.restaurant.waiter.service;

import liga.restaurant.waiter.entity.Menu;
import liga.restaurant.waiter.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuService {
    private final MenuRepository repo;

    public Menu save(Menu menu) {
        Menu saved = repo.save(menu);
        log.debug("Пункт меню сохранён: {}", saved);
        return saved;
    }

    public List<Menu> findAll() {
        log.info("Получение всех пунктов меню");
        List<Menu> menus = repo.findAll();
        log.debug("Найдено пунктов меню: {}", menus.size());
        return menus;
    }

    public Menu findById(Long id) {
        log.info("Получение пункта меню по id={}", id);
        return repo.findById(id)
                .map(menu -> {
                    log.debug("Пункт меню найден: {}", menu);
                    return menu;
                })
                .orElseGet(() -> {
                    log.warn("Пункт меню не найден: id={}", id);
                    return null;
                });
    }

    public void delete(Long id) {
        log.info("Удаление пункта меню: id={}", id);
        if (!repo.existsById(id)) {
            log.warn("Пункт меню не найден для удаления: id={}", id);
            return;
        }
        repo.deleteById(id);
        log.info("Пункт меню удалён: id={}", id);
    }
}
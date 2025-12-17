package liga.restaurant.waiter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import liga.restaurant.waiter.entity.Menu;
import liga.restaurant.waiter.repository.MenuRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuService {
    public Menu save(Menu menu) {
        Menu saved = repo.save(menu);
        log.debug("Menu item saved: {}", saved);
        return saved;
    }

    public List<Menu> findAll() {
        log.info("Fetching all menu items");
        List<Menu> menus = repo.findAll();
        log.debug("Found {} menu items", menus.size());
        return menus;
    }

    public Menu findById(Long id) {
        log.info("Fetching menu item by id: {}", id);
        return repo.findById(id)
                .map(menu -> {
                    log.debug("Found menu item: {}", menu);
                    return menu;
                })
                .orElseGet(() -> {
                    log.warn("Menu item not found: id={}", id);
                    return null;
                });
    }

    public void delete(Long id) {
        log.info("Deleting menu item: id={}", id);
        if (!repo.existsById(id)) {
            log.warn("Menu item not found for deletion: id={}", id);
            return;
        }
        repo.deleteById(id);
        log.info("Menu item deleted: id={}", id);
    }

    private final MenuRepository repo;
}
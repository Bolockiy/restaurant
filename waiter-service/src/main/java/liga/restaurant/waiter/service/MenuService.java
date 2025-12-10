package liga.restaurant.waiter.service;

import org.springframework.stereotype.Service;
import liga.restaurant.waiter.entity.Menu;
import liga.restaurant.waiter.repository.MenuRepository;

import java.util.List;

@Service
public class MenuService {

    private final MenuRepository repo;

    public MenuService(MenuRepository repo) {
        this.repo = repo;
    }

    public Menu save(Menu menu) {
        return repo.save(menu);
    }

    public List<Menu> findAll() {
        return repo.findAll();
    }

    public Menu findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}

package pl.edu.agh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.model.Category;
import pl.edu.agh.model.User;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUser(User user);
    Category findByIdAndUser(Integer id, User user);
    Category findByTitleAndUser(String title, User user);
}

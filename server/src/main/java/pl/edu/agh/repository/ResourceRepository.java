package pl.edu.agh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.model.Category;
import pl.edu.agh.model.Resource;
import pl.edu.agh.model.User;

import java.util.List;

@Transactional
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findAllByUserId(Integer userId);
    List<Resource> findAllByCategoryIdAndUser(Integer categoryId, User user);
    void deleteAll();
    int deleteByIdAndUserId(Integer id, Integer userId);
    Resource findByIdAndUser(Integer id, User user);
}

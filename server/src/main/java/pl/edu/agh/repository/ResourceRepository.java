package pl.edu.agh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.model.Resource;

import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findAllByUserId(Integer userId);
    void deleteAll();
}

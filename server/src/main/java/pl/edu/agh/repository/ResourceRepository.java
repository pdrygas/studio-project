package pl.edu.agh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.model.Resource;

import java.util.List;

@Transactional
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findAllByUserId(Integer userId);
    void deleteAll();
    int deleteByIdAndUserId(Integer id, Integer userId);
}

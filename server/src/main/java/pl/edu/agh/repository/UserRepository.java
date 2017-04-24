package pl.edu.agh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByToken(String token);
    User findByUsername(String username);
    void deleteAll();
}

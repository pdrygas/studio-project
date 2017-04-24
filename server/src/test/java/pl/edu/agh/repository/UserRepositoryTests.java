package pl.edu.agh.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.agh.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepo;

    private final String token = "secret_token";

    @Test
    public void findUserByToken() {
        User user = userRepo.findByToken(token);
        Assert.assertEquals("user", user.getUsername());
    }

    @Test
    public void userNotFoundByToken() {
        final String wrongToken = token + "x";
        User user = userRepo.findByToken(wrongToken);
        Assert.assertEquals(null, user);
    }
}

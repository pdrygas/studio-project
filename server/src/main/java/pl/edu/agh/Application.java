package pl.edu.agh;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.edu.agh.model.User;
import pl.edu.agh.repository.UserRepository;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public InitializingBean insertDefaultUsers() {
        return new InitializingBean() {
            @Autowired
            private UserRepository userRepo;

            @Override
            public void afterPropertiesSet() throws Exception {
                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setToken("secret_token");

                try {
                    userRepo.save(user);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}

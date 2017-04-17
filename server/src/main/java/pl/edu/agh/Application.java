package pl.edu.agh;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.edu.agh.model.User;
import pl.edu.agh.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
                user.setPassword(hashPassword("user"));
                user.setToken("secret_token");

                try {
                    userRepo.save(user);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }

            private String hashPassword(String password) {
                try {
                    MessageDigest digest = MessageDigest.getInstance("SHA-256");
                    byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < hash.length; i++) {
                        sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
                    }

                    return sb.toString();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                return "";
            }
        };
    }
}

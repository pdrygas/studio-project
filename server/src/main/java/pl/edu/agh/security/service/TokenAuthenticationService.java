package pl.edu.agh.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.edu.agh.model.User;
import pl.edu.agh.security.UserAuthentication;
import pl.edu.agh.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class TokenAuthenticationService {
    private final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";
    @Autowired
    private UserRepository userRepo;

    public void addAuthentication(HttpServletResponse response, UserAuthentication auth) {
        User user = auth.getDetails();
        response.addHeader(AUTH_HEADER_NAME, user.getToken());
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(AUTH_HEADER_NAME);
        if(token != null) {
            User user = userRepo.findByToken(token);
            if(user != null) {
                return new UserAuthentication(user);
            }
        }
        return null;
    }
}

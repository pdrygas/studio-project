package pl.edu.agh.controller.rest;

import org.springframework.web.bind.annotation.*;
import pl.edu.agh.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
public class UserController extends RestApiController {

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public @ResponseBody String getResources(HttpServletRequest request) {
        return result(saveUser(request.getParameter("username"), request.getParameter("password")));
    }

    private boolean saveUser(String username, String password) {
        if(password.length() < 4) {
            return false;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(org.apache.commons.codec.digest.DigestUtils.sha256Hex(password));
        user.setToken(generateToken());
        return saveEntity(userRepo, user);
    }

    private String generateToken() {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        return token.substring(0, 32);
    }
}

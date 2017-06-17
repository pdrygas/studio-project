package pl.edu.agh.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.model.Image;
import pl.edu.agh.model.User;
import pl.edu.agh.repository.ImageRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ImageController extends RestApiController {
    @Autowired
    private ImageRepository imageRepo;

    @RequestMapping(method = RequestMethod.GET, value = "/images")
    public @ResponseBody String getResources(@RequestHeader(AUTH_HEADER_NAME) String token) {
        User user = userRepo.findByToken(token);
        List<Image> resources = imageRepo.findAllByUser(user);
        return gson.toJson(resources);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/images")
    public String addResource(@RequestHeader(AUTH_HEADER_NAME) String token, HttpServletRequest request) {
        User user = userRepo.findByToken(token);
        return result(saveImage(user, request.getParameter("url")));
    }

    private boolean saveImage(User user, String url) {
        Image image = new Image();
        image.setUrl(url);
        image.setUser(user);
        return saveEntity(imageRepo, image);
    }
}

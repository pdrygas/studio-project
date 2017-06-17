package pl.edu.agh.controller.rest;

import org.springframework.web.bind.annotation.*;
import pl.edu.agh.model.Resource;
import pl.edu.agh.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ResourceController extends RestApiController {

    @RequestMapping(method = RequestMethod.GET, value = "/resources")
    public @ResponseBody
    String getResources(@RequestHeader(AUTH_HEADER_NAME) String token) {
        User user = userRepo.findByToken(token);
        List<Resource> resources = resourceRepo.findAllByUserId(user.getId());
        return gson.toJson(resources);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/resources")
    public String addResource(@RequestHeader(AUTH_HEADER_NAME) String token, HttpServletRequest request) {
        User user = userRepo.findByToken(token);
        return result(saveResource(user, request.getParameter("title"), request.getParameter("content"), request.getParameter("category")));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/resources/{id}")
    public String deleteResource(@RequestHeader(AUTH_HEADER_NAME) String token, @PathVariable("id") Integer id) {
        User user = userRepo.findByToken(token);
        return result(resourceRepo.deleteByIdAndUserId(id, user.getId()) > 0);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/resources/{id}")
    public String resource(@RequestHeader(AUTH_HEADER_NAME) String token, @PathVariable("id") Integer id) {
        User user = userRepo.findByToken(token);
        Resource resource = resourceRepo.findByIdAndUser(id, user);
        return gson.toJson(resource);
    }

    private boolean saveResource(User user, String title, String content, String categoryName) {
        Resource resource = new Resource();
        resource.setUser(user);
        resource.setTitle(title);
        resource.setContent(content);
        resource.setCategory(categoryRepo.findByTitleAndUser(categoryName, user));

        return saveEntity(resourceRepo, resource);
    }
}

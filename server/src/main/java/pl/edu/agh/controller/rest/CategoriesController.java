package pl.edu.agh.controller.rest;

import org.springframework.web.bind.annotation.*;
import pl.edu.agh.model.Category;
import pl.edu.agh.model.Resource;
import pl.edu.agh.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class CategoriesController extends RestApiController {

    @RequestMapping(method = RequestMethod.GET, value = "/categories")
    public String getCategories(@RequestHeader(AUTH_HEADER_NAME) String token) {
        User user = userRepo.findByToken(token);
        List<Category> categories = categoryRepo.findByUser(user);
        return gson.toJson(categories);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/categories")
    public String addCategory(@RequestHeader(AUTH_HEADER_NAME) String token, HttpServletRequest request) {
        User user = userRepo.findByToken(token);
        return result(saveCategory(user, request.getParameter("title")));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/categories/{id}")
    public String category(@RequestHeader(AUTH_HEADER_NAME) String token, @PathVariable("id") Integer id) {
        User user = userRepo.findByToken(token);
        Category category = categoryRepo.findByIdAndUser(id, user);
        return gson.toJson(category);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/categories/{id}/resources")
    public String resourcesInCategory(@RequestHeader(AUTH_HEADER_NAME) String token, @PathVariable("id") Integer id) {
        User user = userRepo.findByToken(token);
        List<Resource> resources = resourceRepo.findAllByCategoryIdAndUser(id, user);
        return gson.toJson(resources);
    }

    private boolean saveCategory(User user, String title) {
        Category category = new Category();
        category.setUser(user);
        category.setTitle(title);

        return saveEntity(categoryRepo, category);
    }

}

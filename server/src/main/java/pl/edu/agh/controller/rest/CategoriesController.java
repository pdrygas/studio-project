package pl.edu.agh.controller.rest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
        return categoriesToJson(categories).toString();
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
        return categoryToJson(category).toString();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/categories/{id}/resources")
    public String resourcesInCategory(@RequestHeader(AUTH_HEADER_NAME) String token, @PathVariable("id") Integer id) {
        User user = userRepo.findByToken(token);
        List<Resource> resources = resourceRepo.findAllByCategoryIdAndUser(id, user);
        return resourcesToJson(resources).toString();
    }

    private boolean saveCategory(User user, String title) {
        Category category = new Category();
        category.setUser(user);
        category.setTitle(title);

        return saveEntity(categoryRepo, category);
    }

    private JSONArray categoriesToJson(List<Category> categories) {
        JSONArray result = new JSONArray();
        categories.forEach(category -> result.put(categoryToJson(category)));
        return result;
    }

    private JSONObject categoryToJson(Category category) {
        JSONObject object = new JSONObject();
        try {
            object.put("id", category.getId());
            object.put("title", category.getTitle());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

}

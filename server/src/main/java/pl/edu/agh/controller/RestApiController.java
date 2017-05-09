package pl.edu.agh.controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.model.Category;
import pl.edu.agh.model.Resource;
import pl.edu.agh.model.User;
import pl.edu.agh.repository.CategoryRepository;
import pl.edu.agh.repository.ResourceRepository;
import pl.edu.agh.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestApiController {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ResourceRepository resourceRepo;
    @Autowired
    private CategoryRepository categoryRepo;
    private final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";

    @RequestMapping(method = RequestMethod.POST, value = "/test")
    public @ResponseBody String test() {
        return "workin biatch!";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/resources", produces = "application/json")
    public @ResponseBody String getResources(@RequestHeader(AUTH_HEADER_NAME) String token) {
        User user = userRepo.findByToken(token);
        List<Resource> resources = resourceRepo.findAllByUserId(user.getId());

        return resourcesToJson(resources).toString();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/resources")
    public String addResource(@RequestHeader(AUTH_HEADER_NAME) String token, HttpServletRequest request) {
        User user = userRepo.findByToken(token);

        if(saveResource(user, request.getParameter("title"), request.getParameter("content"))) {
            return "{\"result\": \"ok\"}";
        }
        return "{\"result\": \"error\"}";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/resources/{id}")
    public String deleteResource(@RequestHeader(AUTH_HEADER_NAME) String token, @PathVariable("id") Integer id) {
        User user = userRepo.findByToken(token);
        if(resourceRepo.deleteByIdAndUserId(id, user.getId()) > 0) {
            return "{\"result\": \"ok\"}";
        }
        return "{\"result\": \"error\"}";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/resources/{id}")
    public String resource(@RequestHeader(AUTH_HEADER_NAME) String token, @PathVariable("id") Integer id) {
        User user = userRepo.findByToken(token);
        Resource resource = resourceRepo.findByIdAndUser(id, user);
        return resourceToJson(resource).toString();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/categories")
    public String getCategories(@RequestHeader(AUTH_HEADER_NAME) String token) {
        User user = userRepo.findByToken(token);
        List<Category> categories = categoryRepo.findByUser(user);
        return categoriesToJson(categories).toString();
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

    private JSONArray resourcesToJson(List<Resource> resources) {
        JSONArray result = new JSONArray();
        resources.forEach(resource -> result.put(resourceToJson(resource)));
        return result;
    }

    private JSONObject resourceToJson(Resource resource) {
        JSONObject object = new JSONObject();
        try {
            object.put("id", resource.getId());
            object.put("title", resource.getTitle());
            object.put("content", resource.getContent());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    private boolean saveResource(User user, String title, String content) {
        Resource resource = new Resource();
        resource.setUser(user);
        resource.setTitle(title);
        resource.setContent(content);

        try {
            resourceRepo.save(resource);
        } catch (Exception e) {
            return false;
        }
        return true;
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

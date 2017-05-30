package pl.edu.agh.controller.rest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.model.Category;
import pl.edu.agh.model.Resource;
import pl.edu.agh.model.User;
import pl.edu.agh.repository.CategoryRepository;
import pl.edu.agh.repository.ResourceRepository;
import pl.edu.agh.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

@RequestMapping("/api")
public class RestApiController {
    @Autowired
    protected UserRepository userRepo;
    @Autowired
    protected ResourceRepository resourceRepo;
    @Autowired
    protected CategoryRepository categoryRepo;
    protected final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";

    protected String result(boolean success) {
        if(success) {
            return "{\"result\": \"ok\"}";
        }
        return "{\"result\": \"error\"}";
    }

    protected JSONArray resourcesToJson(List<Resource> resources) {
        JSONArray result = new JSONArray();
        resources.forEach(resource -> result.put(resourceToJson(resource)));
        return result;
    }

    protected JSONObject resourceToJson(Resource resource) {
        JSONObject object = new JSONObject();
        try {
            object.put("id", resource.getId());
            object.put("title", resource.getTitle());
            object.put("content", resource.getContent());
            if(resource.getCategory() != null) {
                object.put("category", resource.getCategory().getTitle());
            } else {
                object.put("category", null);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    protected <T extends JpaRepository<S, Long>, S> boolean saveEntity(T repo, S object) {
        try {
            repo.save(object);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}

package pl.edu.agh.controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.model.Resource;
import pl.edu.agh.model.User;
import pl.edu.agh.repository.ResourceRepository;
import pl.edu.agh.repository.UserRepository;

import java.util.List;

@RestController
public class RestApiController {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ResourceRepository resourceRepo;
    private final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";

    @RequestMapping(method = RequestMethod.POST, value = "/test")
    public @ResponseBody String test() {
        return "workin biatch!";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/resources", produces = "application/json")
    public @ResponseBody String getResources(@RequestHeader(AUTH_HEADER_NAME) String token) {
        User user = userRepo.findByToken(token);
        List<Resource> resources = resourceRepo.findAllByUserId(user.getId());

        return resourcesToJson(resources);
    }

    private String resourcesToJson(List<Resource> resources) {
        JSONArray result = new JSONArray();

        resources.stream().forEach(resource -> {
            JSONObject object = new JSONObject();
            try {
                object.put("id", resource.getId());
                object.put("title", resource.getTitle());
                object.put("content", resource.getContent());
                result.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        return result.toString();
    }
}

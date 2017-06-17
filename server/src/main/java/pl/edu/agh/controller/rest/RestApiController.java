package pl.edu.agh.controller.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.repository.CategoryRepository;
import pl.edu.agh.repository.ResourceRepository;
import pl.edu.agh.repository.UserRepository;

@RequestMapping("/api")
public class RestApiController {
    @Autowired
    protected UserRepository userRepo;
    @Autowired
    protected ResourceRepository resourceRepo;
    @Autowired
    protected CategoryRepository categoryRepo;

    protected Gson gson;
    protected final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";


    public RestApiController() {
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        gson = builder.create();
    }

    protected String result(boolean success) {
        if(success) {
            return "{\"result\": \"ok\"}";
        }
        return "{\"result\": \"error\"}";
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

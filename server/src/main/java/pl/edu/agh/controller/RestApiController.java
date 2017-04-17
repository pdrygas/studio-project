package pl.edu.agh.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {
    @RequestMapping(method = RequestMethod.POST, value = "/test")
    public @ResponseBody String test() {
        return "workin biatch!";
    }
}

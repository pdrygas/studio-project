package pl.edu.agh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PagesController {
    @RequestMapping(method = RequestMethod.GET, value = "/resources")
    public String resources() {
        return "resources";
    }
}

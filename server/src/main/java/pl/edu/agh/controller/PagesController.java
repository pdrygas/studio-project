package pl.edu.agh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PagesController {
    @RequestMapping(method = RequestMethod.GET, value = "/resources")
    public String resources() {
        return "resources";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/resources/{id}")
    public String resource(@PathVariable Integer id) {
        return "resource";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String login() {
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/register")
    public String register() {
        return "register";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/categories")
    public String categories() {
        return "categories";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/categories/{id}")
    public String category(@PathVariable Integer id) {
        return "category";
    }
}

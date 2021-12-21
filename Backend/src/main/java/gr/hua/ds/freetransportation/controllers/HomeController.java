package gr.hua.ds.freetransportation.controllers;

import gr.hua.ds.freetransportation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    private UserService service;

    @GetMapping("/")
    public String viewIndex() {
        return "index";
    }

}

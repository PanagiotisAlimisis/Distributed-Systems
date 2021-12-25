package gr.hua.ds.freetransportation;

import gr.hua.ds.freetransportation.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MainController {

    @GetMapping("/login")
    public String loginUser(@RequestBody User user) {
        return "login";
    }

}

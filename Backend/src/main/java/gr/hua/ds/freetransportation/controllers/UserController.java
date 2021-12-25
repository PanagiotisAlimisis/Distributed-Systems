package gr.hua.ds.freetransportation.controllers;

import gr.hua.ds.freetransportation.entities.User;
import gr.hua.ds.freetransportation.exceptions.NoSuchUserException;
import gr.hua.ds.freetransportation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User getOneUser(@PathVariable int id) {
        User user;
        try {
            user = service.getOneUser(id);
        } catch (NoSuchUserException e) {
            user = null;
        }
        return user;
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User newUser) {
        return service.saveUser(newUser);
    }

}

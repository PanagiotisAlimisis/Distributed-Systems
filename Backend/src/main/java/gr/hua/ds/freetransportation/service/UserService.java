package gr.hua.ds.freetransportation.service;

import gr.hua.ds.freetransportation.dao.UserRepository;
import gr.hua.ds.freetransportation.entities.Role;
import gr.hua.ds.freetransportation.entities.RoleTypes;
import gr.hua.ds.freetransportation.entities.User;
import gr.hua.ds.freetransportation.exceptions.NoSuchUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        user.setRole(new Role(RoleTypes.TRANSPORTATION_EMPLOYEE.toInt()));
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    public User getOneUser(int id) throws NoSuchUserException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NoSuchUserException();
        }
        return user.get();
    }
}

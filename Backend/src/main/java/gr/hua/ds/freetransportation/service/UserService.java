package gr.hua.ds.freetransportation.service;

import gr.hua.ds.freetransportation.dao.UserRepository;
import gr.hua.ds.freetransportation.entities.Application;
import gr.hua.ds.freetransportation.entities.Role;
import gr.hua.ds.freetransportation.entities.RoleTypes;
import gr.hua.ds.freetransportation.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void createApplication() {
        Application a = new Application(new Date(), "pending", false, false, new Date(), null);

    }
}

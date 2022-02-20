package gr.hua.ds.freetransportation.security;

import gr.hua.ds.freetransportation.dao.UserRepository;
import gr.hua.ds.freetransportation.entities.User;
import gr.hua.ds.freetransportation.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetService implements UserDetailsService {
    private static final String USER_NOT_FOUND_MESSAGE = "Could not find user with email: %s.";

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AdminService service;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.getUserByEmail(email);

        if (user != null) {
            return new UserDet(user);
        }
        throw new UsernameNotFoundException("Could not find user with email: " + email);
    }
}

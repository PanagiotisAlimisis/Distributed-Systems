package gr.hua.ds.freetransportation.registration;

import gr.hua.ds.freetransportation.entities.User;
import gr.hua.ds.freetransportation.security.UserDetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    private EmailValidator emailValidator;
    @Autowired
    private UserDetService userDetService;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("Invalid Email in registration");
        }
        System.out.println("New user registered" + request.getFirstName() + " " + request.getLastName());
        return userDetService.signUpUser(new User(request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword()));
    }

}

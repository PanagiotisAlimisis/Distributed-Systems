package gr.hua.ds.freetransportation.registration.service;

import gr.hua.ds.freetransportation.entities.User;
import gr.hua.ds.freetransportation.admin.service.AdminService;
import gr.hua.ds.freetransportation.registration.RegistrationRequest;
import gr.hua.ds.freetransportation.rest_api.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RegistrationService {

    @Autowired
    private AdminService userService;

    /**
     * @param request
     * @return 400 if request is bad or user already exists. Checks with email.
     *         200 upon succession.
     */
    public ResponseEntity<User> register(RegistrationRequest request) {
        if ( (!requestIsOk(request)) || (!userService.isEmailUnique(null, request.getEmail())) ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
        User user = new User(request.getFirstName(), request.getLastName(),  request.getEmail(), request.getPassword());
        userService.saveUser(user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    private boolean requestIsOk(RegistrationRequest request) {
        if (request.getFirstName().isEmpty() || request.getFirstName() == null ||
            request.getLastName().isEmpty() || request.getLastName() == null ||
            request.getEmail().isEmpty() || request.getEmail() == null ||
            request.getPassword().isEmpty() || request.getPassword() == null) {
            return false;
        }
        return true;
    }

}

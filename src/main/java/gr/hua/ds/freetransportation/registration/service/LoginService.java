package gr.hua.ds.freetransportation.registration.service;

import gr.hua.ds.freetransportation.entities.User;
import gr.hua.ds.freetransportation.registration.LoginRequest;
import gr.hua.ds.freetransportation.security.UserDet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 400 if request is bad or user not exists.
     * 200 upon succession.
     * @param request
     * @return
     */
    public ResponseEntity<User> login(LoginRequest request) {
        if (!requestIsOk(request)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            UserDet u = (UserDet) authentication.getPrincipal();

            User user = u.getUser();

            return ResponseEntity.ok(user);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    private boolean requestIsOk(LoginRequest request) {
        if (    request.getEmail().isEmpty() || request.getEmail() == null ||
                request.getPassword().isEmpty() || request.getPassword() == null) {
            return false;
        }
        return true;
    }
}

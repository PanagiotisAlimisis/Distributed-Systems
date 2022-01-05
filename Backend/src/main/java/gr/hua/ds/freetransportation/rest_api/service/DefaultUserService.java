package gr.hua.ds.freetransportation.rest_api.service;

import gr.hua.ds.freetransportation.dao.UnemploymentApplicationRepository;
import gr.hua.ds.freetransportation.dao.UserRepository;
import gr.hua.ds.freetransportation.entities.UnemploymentApplication;
import gr.hua.ds.freetransportation.entities.User;
import gr.hua.ds.freetransportation.security.UserDet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UnemploymentApplicationRepository applicationRepository;

    public ResponseEntity<?> submit(UnemploymentApplication application) {
        UserDet userDet = (UserDet) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDet.getUser();
        int pendingApplications = applicationRepository.countPendingApplications(user.getId());

        if (pendingApplications > 0) {
            return new ResponseEntity<>("User: " + user.getFirstName() + " has already submitted an application", HttpStatus.METHOD_NOT_ALLOWED);
        }

        application.setUser(user);
        applicationRepository.save(application);
        return new ResponseEntity<>("Unemployment application successfully submitted.", HttpStatus.OK);
    }
}

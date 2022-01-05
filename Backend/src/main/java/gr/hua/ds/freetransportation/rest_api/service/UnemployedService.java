package gr.hua.ds.freetransportation.rest_api.service;

import gr.hua.ds.freetransportation.dao.FreeTransportationApplicationRepository;
import gr.hua.ds.freetransportation.entities.FreeTransportationApplication;
import gr.hua.ds.freetransportation.entities.User;
import gr.hua.ds.freetransportation.security.UserDet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UnemployedService {

    @Autowired
    private FreeTransportationApplicationRepository applicationRepository;

    public ResponseEntity<?> submit(FreeTransportationApplication application) {
        UserDet userDet = (UserDet) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDet.getUser();
        int pendingApplications = applicationRepository.countPendingApplications(user.getId());

        if (pendingApplications > 0) {
            return new ResponseEntity<>("User: " + user.getFirstName() + " has already submitted an application", HttpStatus.METHOD_NOT_ALLOWED);
        }

        application.setUser(user);
        applicationRepository.save(application);
        return ResponseEntity.ok("Application saved successfully.");
    }

}

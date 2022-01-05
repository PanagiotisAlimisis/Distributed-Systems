package gr.hua.ds.freetransportation.rest_api.service;

import gr.hua.ds.freetransportation.dao.FreeTransportationApplicationRepository;
import gr.hua.ds.freetransportation.dao.RoleRepository;
import gr.hua.ds.freetransportation.dao.UnemploymentApplicationRepository;
import gr.hua.ds.freetransportation.dao.UserRepository;
import gr.hua.ds.freetransportation.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OaedEmployeeService {

    @Autowired
    private UnemploymentApplicationRepository unemploymentApplicationRepository;

    @Autowired
    private FreeTransportationApplicationRepository freeTransportationApplicationRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> listUnemploymentApplications() {
        List<UnemploymentApplication> unemploymentApplications = unemploymentApplicationRepository.findPendingApplications();
        return ResponseEntity.ok(unemploymentApplications);
    }

    public ResponseEntity<?> evaluateUnemploymentApplication(String id, String decision) {
        Integer applicationId = null;

        try {
            applicationId = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("First parameter should be a number.", HttpStatus.BAD_REQUEST);
        }

        if (!decision.equals("ACCEPT") && !decision.equals("REJECT")) {
            return new ResponseEntity<>("The decision should be either ACCEPT or REJECT", HttpStatus.BAD_REQUEST);
        }

        Optional<UnemploymentApplication> application = unemploymentApplicationRepository.findById(applicationId);

        if (application.isEmpty()) {
            return new ResponseEntity<>("Application not found.", HttpStatus.NOT_FOUND);
        }

        application.get().setStatus(decision);

        if (decision.equals("ACCEPT")) {
            updateUsersRole(application.get().getUser());
        }

        unemploymentApplicationRepository.save(application.get());
        return new ResponseEntity<>("Application's status has been updated.", HttpStatus.OK);
    }

    public ResponseEntity<?> listFreeTransportationApplications() {
        List<FreeTransportationApplication> applications = freeTransportationApplicationRepository.findUnansweredApplications();
        return ResponseEntity.ok(applications);
    }

    public ResponseEntity<?> evaluateFreeTransportationApplication(String id, String decision) {
        Integer applicationId = null;

        try {
            applicationId = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("First parameter should be a number.", HttpStatus.BAD_REQUEST);
        }

        if (!decision.equals("ACCEPT") && !decision.equals("REJECT")) {
            return new ResponseEntity<>("The decision should be either ACCEPT or REJECT", HttpStatus.BAD_REQUEST);
        }

        Optional<FreeTransportationApplication> application = freeTransportationApplicationRepository.findById(applicationId);

        if (application.isEmpty()) {
            return new ResponseEntity<>("Application not found.", HttpStatus.NOT_FOUND);
        }

        if (decision.equals("ACCEPT")) {
            application.get().setValidated(true);
        } else {
            application.get().setValidated(false);
            application.get().setStatus("REJECT");
        }

        freeTransportationApplicationRepository.save(application.get());
        return new ResponseEntity<>("Application's status has been updated.", HttpStatus.OK);
    }

    private void updateUsersRole(User user) {
        Role role = roleRepository.findById(RoleTypes.UNEMPLOYED.toInt()).get();
        user.setRole(role);
        userRepository.save(user);
    }

}

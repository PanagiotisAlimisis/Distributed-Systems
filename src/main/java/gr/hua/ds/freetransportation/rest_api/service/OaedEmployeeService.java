package gr.hua.ds.freetransportation.rest_api.service;

import gr.hua.ds.freetransportation.RoleTypes;
import gr.hua.ds.freetransportation.Status;
import gr.hua.ds.freetransportation.dao.FreeTransportationApplicationRepository;
import gr.hua.ds.freetransportation.dao.RoleRepository;
import gr.hua.ds.freetransportation.dao.UnemploymentApplicationRepository;
import gr.hua.ds.freetransportation.dao.UserRepository;
import gr.hua.ds.freetransportation.entities.*;
import gr.hua.ds.freetransportation.rest_api.ApplicationsResponse;
import gr.hua.ds.freetransportation.rest_api.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class OaedEmployeeService {

    public static final int APPLICATIONS_PER_PAGE = 10;

    @Autowired
    private UnemploymentApplicationRepository unemploymentApplicationRepository;

    @Autowired
    private FreeTransportationApplicationRepository freeTransportationApplicationRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> listUnemploymentApplications(int pageNumber, String sortField, String sortDir) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, APPLICATIONS_PER_PAGE, sort);
        ApplicationsResponse<UnemploymentApplication> response = new ApplicationsResponse<>();
        Page<UnemploymentApplication> pendingApplications = unemploymentApplicationRepository.findPendingApplications(pageable);
        response.setTotalPages(pendingApplications.getTotalPages());
        response.setApplications(pendingApplications.getContent());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> evaluateUnemploymentApplication(String id, String decision) {
        Integer applicationId = null;

        try {
            applicationId = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "First parameter should be a number.");
//            return new ResponseEntity<>("First parameter should be a number.", HttpStatus.BAD_REQUEST);
        }

        if (!decision.equalsIgnoreCase(Status.ACCEPT.toString()) && !decision.equalsIgnoreCase(Status.REJECT.toString())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The decision should be either ACCEPT or REJECT");
//            return new ResponseEntity<>("The decision should be either ACCEPT or REJECT", HttpStatus.BAD_REQUEST);
        }

        Optional<UnemploymentApplication> application = unemploymentApplicationRepository.findById(applicationId);
        if (application.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found.");
//            return new ResponseEntity<>("Application not found.", HttpStatus.NOT_FOUND);
        }

        application.get().setStatus(decision);

        if (decision.equals(Status.ACCEPT.toString())) {
            updateUsersRole(application.get().getUser());
        }

        unemploymentApplicationRepository.save(application.get());
        return ResponseEntity.ok(new Response(200, "Application's status has been updated."));
    }

    public ResponseEntity<?> listFreeTransportationApplications(int pageNumber, String sortField, String sortDir) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, APPLICATIONS_PER_PAGE, sort);
        ApplicationsResponse<FreeTransportationApplication> response = new ApplicationsResponse<>();
        Page<FreeTransportationApplication> pendingApplications = freeTransportationApplicationRepository.findUnansweredApplications(pageable);
        response.setTotalPages(pendingApplications.getTotalPages());
        response.setApplications(pendingApplications.getContent());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> evaluateFreeTransportationApplication(String id, String decision) {
        Integer applicationId = null;

        try {
            applicationId = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "First parameter should be a number.");
//            return new ResponseEntity<>("First parameter should be a number.", HttpStatus.BAD_REQUEST);
        }

        if (!decision.equals(Status.ACCEPT.toString()) && !decision.equals(Status.REJECT.toString())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The decision should be either ACCEPT or REJECT");
//            return new ResponseEntity<>("The decision should be either ACCEPT or REJECT", HttpStatus.BAD_REQUEST);
        }

        Optional<FreeTransportationApplication> application = freeTransportationApplicationRepository.findById(applicationId);

        if (application.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found.");
//            return new ResponseEntity<>("Application not found.", HttpStatus.NOT_FOUND);
        }

        if (decision.equals(Status.ACCEPT.toString())) {
            application.get().setValidated(true);
        } else {
            application.get().setValidated(false);
            application.get().setStatus(Status.REJECT.toString());
        }

        freeTransportationApplicationRepository.save(application.get());
        return ResponseEntity.ok(new Response(200, "Application's status has been updated."));
    }

    private void updateUsersRole(User user) {
        Role role = roleRepository.findByRoleName(RoleTypes.UNEMPLOYED.toString());
        user.setRole(role);
        userRepository.save(user);
    }

}

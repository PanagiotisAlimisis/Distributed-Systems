package gr.hua.ds.freetransportation.rest_api.service;

import gr.hua.ds.freetransportation.Status;
import gr.hua.ds.freetransportation.dao.FreeTransportationApplicationRepository;
import gr.hua.ds.freetransportation.entities.FreeTransportationApplication;
import gr.hua.ds.freetransportation.rest_api.ApplicationsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransportationEmployeeService {

    private static final int APPLICATIONS_PER_PAGE = 10;

    @Autowired
    private FreeTransportationApplicationRepository applicationRepository;

    public ResponseEntity<?> listFreeTransportationApplications(int pageNumber, String sortField, String sortDir) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, APPLICATIONS_PER_PAGE, sort);
        ApplicationsResponse<FreeTransportationApplication> response = new ApplicationsResponse<>();
        Page<FreeTransportationApplication> applications = applicationRepository.findUnansweredAndValidatedApplications(pageable);
        response.setTotalPages(applications.getTotalPages());
        response.setApplications(applications.getContent());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> evaluateFreeTransportationApplication(String id, String decision) {
        Integer applicationId = null;

        try {
            applicationId = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("First parameter should be a number.", HttpStatus.BAD_REQUEST);
        }

        if (!decision.equals(Status.ACCEPT.toString()) && !decision.equals(Status.REJECT.toString())) {
            return new ResponseEntity<>("The decision should be either ACCEPT or REJECT", HttpStatus.BAD_REQUEST);
        }

        Optional<FreeTransportationApplication> application = applicationRepository.findById(applicationId);

        if (application.isEmpty()) {
            return new ResponseEntity<>("Application not found.", HttpStatus.NOT_FOUND);
        }

        if (decision.equals(Status.ACCEPT.toString())) {
            application.get().setApproved(true);
            application.get().setStatus(Status.ACCEPT.toString());
        } else {
            application.get().setValidated(false);
            application.get().setStatus(Status.REJECT.toString());
        }

        applicationRepository.save(application.get());
        return new ResponseEntity<>("Application's status has been updated.", HttpStatus.OK);
    }
}

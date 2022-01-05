package gr.hua.ds.freetransportation.rest_api.service;

import gr.hua.ds.freetransportation.dao.FreeTransportationApplicationRepository;
import gr.hua.ds.freetransportation.entities.FreeTransportationApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransportationEmployeeService {

    @Autowired
    private FreeTransportationApplicationRepository applicationRepository;

    public ResponseEntity<?> listFreeTransportationApplications() {
        List<FreeTransportationApplication> applications = applicationRepository.findUnansweredAndValidatedApplications();
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

        Optional<FreeTransportationApplication> application = applicationRepository.findById(applicationId);

        if (application.isEmpty()) {
            return new ResponseEntity<>("Application not found.", HttpStatus.NOT_FOUND);
        }

        if (decision.equals("ACCEPT")) {
            application.get().setApproved(true);
            application.get().setStatus("ACCEPT");
        } else {
            application.get().setValidated(false);
            application.get().setStatus("REJECT");
        }

        applicationRepository.save(application.get());
        return new ResponseEntity<>("Application's status has been updated.", HttpStatus.OK);
    }
}

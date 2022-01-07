package gr.hua.ds.freetransportation.rest_api.service;

import gr.hua.ds.freetransportation.dao.FreeTransportationApplicationRepository;
import gr.hua.ds.freetransportation.entities.FreeTransportationApplication;
import gr.hua.ds.freetransportation.entities.User;
import gr.hua.ds.freetransportation.util.FileUtil;
import gr.hua.ds.freetransportation.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UnemployedService {

    @Autowired
    private FreeTransportationApplicationRepository applicationRepository;

    public ResponseEntity<?> submit(FreeTransportationApplication application) {
        if (application.getPhoto() == null) {
            return new ResponseEntity<>("User must provide a photo in his/her application.", HttpStatus.BAD_REQUEST);
        }

        User user = UserUtil.getCurrentUser();

        int pendingApplications = applicationRepository.countPendingApplications(user.getId());

        if (pendingApplications > 0) {
            return new ResponseEntity<>("User: " + user.getFirstName() + " has already submitted an application", HttpStatus.METHOD_NOT_ALLOWED);
        }

        application.setUser(user);
        applicationRepository.save(application);
        return ResponseEntity.ok("Application saved successfully.");
    }

    public ResponseEntity<?> retrieveQRCode(User user) {
        try {
            ByteArrayResource image = FileUtil.getQRCode(user);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentLength(image.contentLength())
                    .body(image);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User doesn't have a qr code.");
        }
    }

}

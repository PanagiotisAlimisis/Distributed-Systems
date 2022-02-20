package gr.hua.ds.freetransportation.rest_api.service;

import gr.hua.ds.freetransportation.dao.FreeTransportationApplicationRepository;
import gr.hua.ds.freetransportation.entities.FreeTransportationApplication;
import gr.hua.ds.freetransportation.entities.User;
import gr.hua.ds.freetransportation.rest_api.Response;
import gr.hua.ds.freetransportation.util.FileUtil;
import gr.hua.ds.freetransportation.util.UserUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UnemployedService {

    @Autowired
    private FreeTransportationApplicationRepository applicationRepository;

    public ResponseEntity<Response> submit(FreeTransportationApplication application) {
        if (application.getPhoto() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User must provide a photo in his/her application.");
//            return new ResponseEntity<>("User must provide a photo in his/her application.", HttpStatus.BAD_REQUEST);
        }

        User user = UserUtil.getCurrentUser();

        int pendingApplications = applicationRepository.countPendingApplications(user.getId());

        if (pendingApplications > 0) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "User: " + user.getFirstName() + " has already submitted an application");
//            return new ResponseEntity<>("User: " + user.getFirstName() + " has already submitted an application", HttpStatus.METHOD_NOT_ALLOWED);
        }

        application.setUser(user);
        applicationRepository.save(application);
        return ResponseEntity.ok(new Response(200, "Application saved successfully."));
//        return ResponseEntity.ok("Application saved successfully.");
    }

    public ResponseEntity<?> retrieveQRCode(User user) {
        try {
            ByteArrayResource image = FileUtil.getQRCode(user);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentLength(image.contentLength())
                    .body(image);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't have a qr code.");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User doesn't have a qr code.");
        }
    }

}

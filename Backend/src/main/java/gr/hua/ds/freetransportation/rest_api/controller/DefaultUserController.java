package gr.hua.ds.freetransportation.rest_api.controller;

import gr.hua.ds.freetransportation.rest_api.service.DefaultUserService;
import gr.hua.ds.freetransportation.entities.UnemploymentApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/unemployment_application")
public class DefaultUserController {

    @Autowired
    private DefaultUserService service;

    @PostMapping
    public ResponseEntity<?> submitUnemploymentApplication(@RequestBody UnemploymentApplication application) {
        return service.submit(application);
    }

}

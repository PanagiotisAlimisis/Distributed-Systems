package gr.hua.ds.freetransportation.rest_api.controller;

import gr.hua.ds.freetransportation.entities.FreeTransportationApplication;
import gr.hua.ds.freetransportation.rest_api.service.UnemployedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/unemployed")
public class UnemployedController {

    @Autowired
    private UnemployedService service;

    @PostMapping("/free_transportation_application")
    public ResponseEntity<?> submitFreeTransportationApplication(@RequestBody FreeTransportationApplication application) {
        return service.submit(application);
    }

}

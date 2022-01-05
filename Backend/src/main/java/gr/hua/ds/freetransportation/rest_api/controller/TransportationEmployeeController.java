package gr.hua.ds.freetransportation.rest_api.controller;

import gr.hua.ds.freetransportation.rest_api.service.TransportationEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/transportation_employee")
public class TransportationEmployeeController {

    @Autowired
    private TransportationEmployeeService service;

    @GetMapping("/list_free_transportation_applications")
    public ResponseEntity<?> listFreeTransportationApplications() {
        return service.listFreeTransportationApplications();
    }

    @PutMapping("/evaluate_free_transportation_applications")
    public ResponseEntity<?> evaluateFreeTransportationApplication(@RequestParam("application_id") String id, @RequestParam("decision") String decision) {
        return service.evaluateFreeTransportationApplication(id, decision);
    }

}

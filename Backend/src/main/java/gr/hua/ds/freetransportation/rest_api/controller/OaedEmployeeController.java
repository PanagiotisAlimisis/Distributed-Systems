package gr.hua.ds.freetransportation.rest_api.controller;

import gr.hua.ds.freetransportation.entities.UnemploymentApplication;
import gr.hua.ds.freetransportation.rest_api.service.OaedEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/oaed_employee")
public class OaedEmployeeController {

    @Autowired
    private OaedEmployeeService service;

    @GetMapping("/list_unemployment_applications")
    public ResponseEntity<?> listUnemploymentApplications() {
        return service.listUnemploymentApplications();
    }

    @PutMapping("/evaluate_unemployment_applications")
    public ResponseEntity<?> evaluateUnemploymentApplication(@RequestParam("application_id") String id, @RequestParam("decision") String decision) {
        return service.evaluateUnemploymentApplication(id, decision);
    }

    @GetMapping("/list_free_transportation_applications")
    public ResponseEntity<?> listFreeTransportationApplications() {
        return service.listFreeTransportationApplications();
    }

    @PutMapping("/evaluate_free_transportation_applications")
    public ResponseEntity<?> evaluateFreeTransportationApplication(@RequestParam("application_id") String id, @RequestParam("decision") String decision) {
        return service.evaluateFreeTransportationApplication(id, decision);
    }



}

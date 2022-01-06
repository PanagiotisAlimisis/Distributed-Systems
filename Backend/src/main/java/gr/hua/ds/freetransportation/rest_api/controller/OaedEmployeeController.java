package gr.hua.ds.freetransportation.rest_api.controller;

import gr.hua.ds.freetransportation.entities.UnemploymentApplication;
import gr.hua.ds.freetransportation.rest_api.service.OaedEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/oaed_employee")
public class OaedEmployeeController {

    @Autowired
    private OaedEmployeeService service;

    @GetMapping("/list_unemployment_applications/{pageNumber}")
    public ResponseEntity<?> listUnemploymentApplications(@PathVariable(name = "pageNumber") int pageNumber,
                                                          @Param("sortField") String sortField,
                                                          @Param("sortDir") String sortDir) {
        return service.listUnemploymentApplications(pageNumber, sortField, sortDir);
    }

    @PutMapping("/evaluate_unemployment_applications")
    public ResponseEntity<?> evaluateUnemploymentApplication(@RequestParam("application_id") String id, @RequestParam("decision") String decision) {
        return service.evaluateUnemploymentApplication(id, decision);
    }

    @GetMapping("/list_free_transportation_applications/{pageNumber}")
    public ResponseEntity<?> listFreeTransportationApplications(@PathVariable(name = "pageNumber") int pageNumber,
                                                                @Param("sortField") String sortField,
                                                                @Param("sortDir") String sortDir) {
        return service.listFreeTransportationApplications(pageNumber, sortField, sortDir);
    }

    @PutMapping("/evaluate_free_transportation_applications")
    public ResponseEntity<?> evaluateFreeTransportationApplication(@RequestParam("application_id") String id, @RequestParam("decision") String decision) {
        return service.evaluateFreeTransportationApplication(id, decision);
    }

}

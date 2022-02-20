package gr.hua.ds.freetransportation.rest_api.controller;

import com.google.zxing.WriterException;
import gr.hua.ds.freetransportation.RoleTypes;
import gr.hua.ds.freetransportation.entities.FreeTransportationApplication;
import gr.hua.ds.freetransportation.entities.UnemploymentApplication;
import gr.hua.ds.freetransportation.entities.User;
import gr.hua.ds.freetransportation.rest_api.service.DefaultUserService;
import gr.hua.ds.freetransportation.rest_api.service.OaedEmployeeService;
import gr.hua.ds.freetransportation.rest_api.service.TransportationEmployeeService;
import gr.hua.ds.freetransportation.rest_api.service.UnemployedService;
import gr.hua.ds.freetransportation.util.FileUtil;
import gr.hua.ds.freetransportation.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api")
public class RestApiController {

    @Autowired
    private OaedEmployeeService oaedEmployeeService;

    @Autowired
    private TransportationEmployeeService transportationEmployeeService;

    @Autowired
    private UnemployedService unemployedService;

    @Autowired
    private DefaultUserService defaultUserService;


    @GetMapping("/getAll/{pageNumber}")
    public ResponseEntity<?> getAllApplications(@PathVariable(name = "pageNumber") int pageNumber,
                                                          @Param("sortField") String sortField,
                                                          @Param("sortDir") String sortDir,
                                                          @Param("type") String type) {

        User user = UserUtil.getCurrentUser();

        if (user.getRole().getName().equalsIgnoreCase(RoleTypes.OAED_EMPLOYEE.toString())) {

            if (type != null && type.equalsIgnoreCase("1")) {
                return oaedEmployeeService.listUnemploymentApplications(pageNumber, sortField, sortDir);
            }

            return oaedEmployeeService.listFreeTransportationApplications(pageNumber, sortField, sortDir);

        } else if (user.getRole().getName().equalsIgnoreCase(RoleTypes.TRANSPORTATION_EMPLOYEE.toString())) {

            return transportationEmployeeService.listFreeTransportationApplications(pageNumber, sortField, sortDir);

        }

        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "User is not allowed to perform this action.");
    }

    @PutMapping("")
    public ResponseEntity<?> evaluate(@RequestParam("application_id") String id,
                                                @RequestParam("decision") String decision,
                                                @Param("type") String type) throws WriterException {

        User user = UserUtil.getCurrentUser();

        if (type != null && type.equalsIgnoreCase("1") && user.getRole().getName().equalsIgnoreCase(RoleTypes.TRANSPORTATION_EMPLOYEE.toString())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not allowed to perform this action.");
        }

        if (user.getRole().getName().equalsIgnoreCase(RoleTypes.OAED_EMPLOYEE.toString())) {

            if (type != null && type.equalsIgnoreCase("1")) {
                return oaedEmployeeService.evaluateUnemploymentApplication(id, decision);
            }

            return oaedEmployeeService.evaluateFreeTransportationApplication(id, decision);

        } else if (user.getRole().getName().equalsIgnoreCase(RoleTypes.TRANSPORTATION_EMPLOYEE.toString())) {

            return transportationEmployeeService.evaluateFreeTransportationApplication(id, decision);

        }

        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "User is not allowed to perform this action.");
    }

    @GetMapping("")
    public ResponseEntity<?> getQRCode() {
        User user = UserUtil.getCurrentUser();
        return unemployedService.retrieveQRCode(user);
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> submit(@RequestParam("photo") MultipartFile multipartFile) {
        FreeTransportationApplication application = new FreeTransportationApplication();
        String fileName = FileUtil.retrievePhoto(multipartFile);

        if (fileName == null) {
            application.setPhoto(null);
        } else {
            application.setPhoto(fileName);
            FileUtil.savePhoto(fileName, multipartFile);
        }

        return unemployedService.submit(application);
    }

    @PostMapping("")
    public ResponseEntity<?> submit(@RequestBody UnemploymentApplication application) {
        return defaultUserService.submit(application);
    }

}

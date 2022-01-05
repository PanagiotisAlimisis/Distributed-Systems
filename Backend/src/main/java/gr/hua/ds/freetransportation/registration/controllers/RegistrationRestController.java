package gr.hua.ds.freetransportation.registration.controllers;

import gr.hua.ds.freetransportation.entities.User;
import gr.hua.ds.freetransportation.registration.RegistrationRequest;
import gr.hua.ds.freetransportation.registration.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/register")
public class RegistrationRestController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<User> register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

}

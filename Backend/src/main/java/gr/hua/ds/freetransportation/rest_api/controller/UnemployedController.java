package gr.hua.ds.freetransportation.rest_api.controller;

import gr.hua.ds.freetransportation.entities.FreeTransportationApplication;
import gr.hua.ds.freetransportation.entities.User;
import gr.hua.ds.freetransportation.rest_api.service.UnemployedService;
import gr.hua.ds.freetransportation.util.FileUtil;
import gr.hua.ds.freetransportation.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/unemployed")
public class UnemployedController {

    @Autowired
    private UnemployedService service;

    @PostMapping("/free_transportation_application")
    public ResponseEntity<?> submitFreeTransportationApplication(@RequestParam("photo") MultipartFile multipartFile) {
        FreeTransportationApplication application = new FreeTransportationApplication();
        String fileName = retrievePhoto(multipartFile);

        if (fileName == null) {
            application.setPhoto(null);
        } else {
            application.setPhoto(fileName);
            savePhoto(fileName, multipartFile);
        }

        return service.submit(application);
    }

    @GetMapping("/get_qr_code")
    public ResponseEntity<?> getQRCode() {
        User user = UserUtil.getCurrentUser();
        return service.retrieveQRCode(user);
    }

    private String retrievePhoto(MultipartFile multipartFile) {
        String fileName = null;
        if (!multipartFile.isEmpty()) {
            fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        }
        return fileName;
    }

    private void savePhoto(String fileName, MultipartFile multipartFile) {
        User user = UserUtil.getCurrentUser();
        String uploadDir = "user-photos/" + user.getId();
        FileUtil.cleanDir(uploadDir);
        try {
            FileUtil.saveMultipartFile(uploadDir, fileName, multipartFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

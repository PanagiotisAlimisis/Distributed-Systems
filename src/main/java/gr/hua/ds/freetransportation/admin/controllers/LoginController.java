package gr.hua.ds.freetransportation.admin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
//@Getter
//@Setter
//@Configuration
//@ConfigurationProperties(prefix = "spring.mail")
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginController {

//    @Autowired
//    private final JavaMailSender mailSender;

//    private String username;


    @GetMapping("/login")
    public String viewLoginPage() throws Exception {
//        this.sendEmail();
        return "login";
    }

//    public void sendEmail() throws Exception {
//        MimeMessage message = mailSender.createMimeMessage();
//
//        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
//
//        mimeMessageHelper.setTo("it21902@hua.gr");
//        mimeMessageHelper.setFrom("it21902@hua.gr");
//        mimeMessageHelper.setSubject("FREE TRANSPORTATION SYSTEM");
//        mimeMessageHelper.setText("You just logged in.");
//
//        mailSender.send(message);
//    }

}

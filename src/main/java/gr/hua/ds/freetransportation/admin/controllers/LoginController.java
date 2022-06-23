package gr.hua.ds.freetransportation.admin.controllers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.mail.internet.MimeMessage;

@Controller
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.mail")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginController {

    @Autowired
    private final JavaMailSender mailSender;

//    private String username;


    @GetMapping("/login")
    public String viewLoginPage() throws Exception {
        this.sendEmail();
        return "login";
    }

    public void sendEmail() throws Exception {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);

        mimeMessageHelper.setTo("it21902@hua.gr");
        mimeMessageHelper.setFrom("it21902@hua.gr");
        mimeMessageHelper.setSubject("FREE TRANSPORTATION SYSTEM");
        mimeMessageHelper.setText("You just logged in.");

        mailSender.send(message);
    }

}

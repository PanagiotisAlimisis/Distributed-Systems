package gr.hua.ds.freetransportation.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.mail")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmailConfiguration {
    private String host;
    private int port;
    private String username;
    private String password;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
//       javaMailSender.setUsername(username);
//        javaMailSender.setPassword(password);

        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true");
        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }
}
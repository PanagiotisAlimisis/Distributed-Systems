package gr.hua.ds.freetransportation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;

import javax.servlet.annotation.MultipartConfig;

//@SpringBootApplication(exclude = {MultipartAutoConfiguration.class})
//@MultipartConfig
@SpringBootApplication
public class FreeTransportationApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreeTransportationApplication.class, args);
    }

}

package ykk.ykk_frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class YkkFrontendApplication {

    public static void main(String[] args) {
        SpringApplication.run(YkkFrontendApplication.class, args);
        System.out.println("Front Web Server on!");
    }

}

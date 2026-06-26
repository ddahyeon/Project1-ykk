package ykk.ykk_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YkkBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(YkkBackendApplication.class, args);
        System.out.println("Backend API Server on!");
    }

}

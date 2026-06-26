package ykk.ykk_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/home")
    public String  home(){
        System.out.println("API Request /home");
        return "Hello World!";
    }

}
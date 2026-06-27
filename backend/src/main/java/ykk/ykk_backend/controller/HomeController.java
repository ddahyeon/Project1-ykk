package ykk.ykk_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ykk.ykk_backend.dto.ResAPI;

@RestController
public class HomeController {
    @GetMapping("/")
    public ResAPI home(){
        System.out.println("API Request /");
        return new ResAPI("Hello World!");
    }

}
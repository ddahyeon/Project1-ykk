package ykk.ykk_backend.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ykk.ykk_backend.dto.ResAPI;
import ykk.ykk_backend.dto.ReqLogin;
import ykk.ykk_backend.dto.ReqSignup;
import ykk.ykk_backend.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;


    @PostMapping("/signup")
    public ResAPI Signup(@Valid @RequestBody ReqSignup reqSignup) {
        System.out.println("API Request /api/user/signup");
        userService.signup(reqSignup);

        return new ResAPI("Hello World!");
    }
    @PostMapping("/login")
    public ResAPI Login(@Valid @RequestBody ReqLogin reqLogin) {
        System.out.println("API Request /api/user/login");
        return new ResAPI(userService.login(reqLogin));
    }

}
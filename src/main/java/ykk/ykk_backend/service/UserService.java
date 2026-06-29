package ykk.ykk_backend.service;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import ykk.ykk_backend.dto.ReqLogin;
import ykk.ykk_backend.dto.ReqSignup;

public interface UserService {
    String signup(@Valid @RequestBody ReqSignup reqSignup);
    String login(@Valid @RequestBody ReqLogin reqLogin);
}

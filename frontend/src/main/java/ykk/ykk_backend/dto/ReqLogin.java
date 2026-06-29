package ykk.ykk_backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ReqLogin {
    @NotNull
    String userid;
    @NotNull
    String password;
}

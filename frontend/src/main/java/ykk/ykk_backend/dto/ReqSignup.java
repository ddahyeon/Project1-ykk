package ykk.ykk_backend.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ReqSignup {
    @NotNull
    String userid;
    @NotNull
    String password;
    @NotNull
    String username;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate birthday;
}

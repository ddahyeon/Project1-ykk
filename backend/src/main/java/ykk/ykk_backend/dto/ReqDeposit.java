package ykk.ykk_backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReqDeposit {
    @NotNull
    String userid;
    @NotNull
    int amount;
}

package ykk.ykk_backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ykk.ykk_backend.common.SavingType;

@Getter
@Setter
@AllArgsConstructor
public class ReqRegist {
    @NotNull
    String userid;
    @NotNull
    SavingType type;
    @NotNull
    int initdeposit;
}

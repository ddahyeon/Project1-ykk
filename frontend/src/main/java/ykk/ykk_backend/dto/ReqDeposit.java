package ykk.ykk_backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ykk.ykk_backend.common.SavingType;

@Getter
@Setter
@AllArgsConstructor
public class ReqDeposit {
    @NotNull
    String userid;
    @NotNull
    int amount;
    SavingType savingtype = SavingType.DEFAULT;

    public void setSavingtype(SavingType savingtype) {
        this.savingtype = (savingtype == null) ? SavingType.DEFAULT : savingtype;

    }

}

package ykk.ykk_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ykk.ykk_backend.common.SavingType;

@Getter
@AllArgsConstructor
public class ResAccount {
    private String account;
    private SavingType savingtype;
    private int interest;
    private float rate;
    private int amount;
    private String start_time;
    private String end_time;
}
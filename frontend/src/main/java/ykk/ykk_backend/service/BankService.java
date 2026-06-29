package ykk.ykk_backend.service;

import org.springframework.web.bind.annotation.RequestBody;
import ykk.ykk_backend.dto.ResAccount;
import ykk.ykk_backend.dto.ReqDeposit;
import ykk.ykk_backend.dto.ReqRegist;

import java.util.List;

public interface BankService {
    List<ResAccount> GetAllAccount(@RequestBody String userid);
    void Deposit(@RequestBody ReqDeposit dto);
    void Withdraw(@RequestBody ReqDeposit dto);
    void RegistGoods(@RequestBody ReqRegist dto);
}

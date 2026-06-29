package ykk.ykk_backend.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ykk.ykk_backend.common.ExternalAPI;
import ykk.ykk_backend.dto.ResAccount;
import ykk.ykk_backend.dto.ResAPI;
import ykk.ykk_backend.dto.ReqDeposit;
import ykk.ykk_backend.service.BankService;
import ykk.ykk_backend.dto.ReqRegist;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bank")
public class BankController {
    @Autowired
    private BankService bankService;

    @GetMapping("/dollar")
    public Map<String, Float> getDollar(){
        System.out.println("API Request /api/bank/dollar");
        return Map.of("dollar", ExternalAPI.GetDollorPrice());
    }

    @PostMapping("/show")
    public List<ResAccount> showmoney(@RequestBody Map<String, String> body){
        System.out.println("API Request /api/bank/show");

        return bankService.GetAllAccount(body.get("userid"));

    }


    @PostMapping("/deposit") // 입금
    public ResAPI deposit(@Valid @RequestBody ReqDeposit reqDeposit) {
        System.out.println("API Request /api/bank/deposit");
        bankService.Deposit(reqDeposit);



        return new ResAPI("success!");
    }
    @PostMapping("/withdraw") // 출금
    public ResAPI withdraw(@Valid @RequestBody ReqDeposit reqDeposit) {
        System.out.println("API Request /api/bank/withdraw");
        bankService.Withdraw(reqDeposit);


        return new ResAPI("success!");
    }
    @PostMapping("/regist") // 적금 가입
    public ResAPI regist(@Valid @RequestBody ReqRegist registDTO){
        System.out.println("API Request /api/bank/regist");
        bankService.RegistGoods(registDTO);
        return new ResAPI("success!");
    }


}

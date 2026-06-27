package ykk.ykk_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ykk.ykk_backend.common.CustomUtil;
import ykk.ykk_backend.common.DepositType;
import ykk.ykk_backend.common.ExternalAPI;
import ykk.ykk_backend.common.SavingType;
import ykk.ykk_backend.dto.ResAccount;
import ykk.ykk_backend.dto.ReqDeposit;
import ykk.ykk_backend.dto.ReqRegist;
import ykk.ykk_backend.entity.AccountEntity;
import ykk.ykk_backend.entity.DepositEntity;
import ykk.ykk_backend.repository.AccountRepository;
import ykk.ykk_backend.repository.DepositRepository;
import ykk.ykk_backend.repository.UserRepository;

import java.util.List;

@Service
public class BankServiceImpl implements BankService{
    @Autowired
    DepositRepository depositRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<ResAccount> GetAllAccount(String userid) {
        List<AccountEntity> accountEntityList = accountRepository.findByUserid(userid);
        if(accountEntityList.isEmpty()){
            throw new RuntimeException("계좌가 없습니다.");
        }

        return accountEntityList.stream()
                .map(account -> new ResAccount(
                        account.getUserid(),
                        account.getAccountnum(),
                        account.getSavingtype(),
                        account.getAmount()))
                .toList();
    }

    @Override
    public void Deposit(ReqDeposit dto) {
        // 계좌 조회
        AccountEntity account = accountRepository
                .findByUseridAndSavingtype(dto.getUserid(), SavingType.DEFAULT)
                .orElseThrow(() -> new RuntimeException("사용자가 없습니다."));

        // 잔액 증가
        account.setAmount(account.getAmount() + dto.getAmount());

        // 입출금 내역 생성
        DepositEntity deposit = DepositEntity.builder()
                .userid(dto.getUserid())
                .iotype(DepositType.DEPOSIT)
                .savingtype(SavingType.DEFAULT)
                .amount(dto.getAmount())
                .build();


        // 저장
        accountRepository.save(account);
        depositRepository.save(deposit);
    }

    @Override
    public void Withdraw(ReqDeposit dto) {
        // 계좌 조회
        AccountEntity account = accountRepository
                .findByUseridAndSavingtype(dto.getUserid(), SavingType.DEFAULT)
                .orElseThrow(() -> new RuntimeException("사용자가 없습니다."));

        // 잔액 증가
        account.setAmount(account.getAmount() - dto.getAmount());

        // 입출금 내역 생성
        DepositEntity deposit = DepositEntity.builder()
                .userid(dto.getUserid())
                .iotype(DepositType.WITHDRAW)
                .savingtype(SavingType.DEFAULT)
                .amount(dto.getAmount())
                .build();

        // 저장
        accountRepository.save(account);
        depositRepository.save(deposit);
    }

    @Override
    public void RegistGoods(ReqRegist dto) {
        AccountEntity accountEntity = AccountEntity.builder()
                .accountnum(CustomUtil.GenerateAccountNumber())
                .userid(dto.getUserid())
                .savingtype(dto.getType())
                .amount(dto.getInitdeposit())
                .build();

        DepositEntity depositEntity = DepositEntity.builder()
                .userid(dto.getUserid())
                .iotype(DepositType.REGISTRY)
                .savingtype(dto.getType())
                .amount(dto.getInitdeposit())
                .build();

        if(dto.getType() == SavingType.DOLLAR){
            float dollar = ExternalAPI.GetDollorPrice();
            float money = dto.getInitdeposit()/dollar;
            accountEntity.setAmount((int)money * 1000);
        }




        accountRepository.save(accountEntity);
        depositRepository.save(depositEntity);
    }


}

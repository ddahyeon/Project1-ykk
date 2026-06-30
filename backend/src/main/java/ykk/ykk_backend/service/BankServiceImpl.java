package ykk.ykk_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

import java.time.LocalDateTime;
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
                        account.getAccountnum(),
                        account.getSavingtype(),
                        account.getInterest(),
                        (float)account.getInterestrate()/100,
                        account.getAmount(),
                        CalcTime(account.getStart_time()),
                        CalcTime(account.getEnd_time())
                        )).toList();
    }

    @Override
    public void Deposit(ReqDeposit dto) {
        if(dto.getSavingtype() == SavingType.DEFAULT){
            // 계좌 조회
            AccountEntity account = accountRepository
                    .findByUseridAndSavingtype(dto.getUserid(), SavingType.DEFAULT)
                    .orElseThrow(() -> new RuntimeException("사용자가 없습니다."));
            // 잔액 증가
            account.setAmount(account.getAmount() + dto.getAmount());
            // 입출금 내역 생성
            DepositEntity deposit = DepositEntity.builder()
                    .accountnum(account.getAccountnum())
                    .iotype(DepositType.DEPOSIT)
                    .savingtype(dto.getSavingtype())
                    .amount(dto.getAmount())
                    .build();
            // 저장
            accountRepository.save(account);
            depositRepository.save(deposit);

        }
        else{
            AccountEntity defaultaccount = accountRepository
                    .findByUseridAndSavingtype(dto.getUserid(), SavingType.DEFAULT)
                    .orElseThrow(() -> new RuntimeException("사용자가 없습니다."));

            // 입출금 내역 생성
            DepositEntity deposit1 = DepositEntity.builder()
                    .accountnum(defaultaccount.getAccountnum())
                    .iotype(DepositType.DEPOSIT)
                    .savingtype(SavingType.DEFAULT)
                    .amount(dto.getAmount())
                    .build();

            defaultaccount.setAmount(defaultaccount.getAmount() - dto.getAmount());
            AccountEntity targetaccount = accountRepository
                    .findByUseridAndSavingtype(dto.getUserid(), dto.getSavingtype())
                    .orElseThrow(() -> new RuntimeException("가입된 상품이 아닙니다."));
            targetaccount.setAmount(targetaccount.getAmount() + dto.getAmount());

            // 입출금 내역 생성
            DepositEntity deposit2 = DepositEntity.builder()
                    .accountnum(targetaccount.getAccountnum())
                    .iotype(DepositType.WITHDRAW)
                    .savingtype(dto.getSavingtype())
                    .amount(dto.getAmount())
                    .build();

            accountRepository.save(defaultaccount);
            depositRepository.save(deposit1);
            accountRepository.save(targetaccount);
            depositRepository.save(deposit2);
        }
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
                .accountnum(account.getAccountnum())
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
        int interestrate = switch (dto.getType()) {
            case SavingType.NORMAL -> 300;
            case SavingType.DOLLAR -> 400;
            case SavingType.COMPOUND -> 500;
            case SavingType.YOUTH -> 600;
            default -> 0;
        };

        interestrate += switch (dto.getPeriod()){
            case 6 -> 0;
            case 12 -> 60;
            case 18 -> 100;
            case 24 -> 150;
            default -> 0;
        };

        LocalDateTime now = LocalDateTime.now();
        int cur = (now.getHour() * 60) + now.getMinute();

        AccountEntity defaultaccount = accountRepository
                .findByUseridAndSavingtype(dto.getUserid(), SavingType.DEFAULT)
                .orElseThrow(() -> new RuntimeException("사용자가 없습니다."));

        defaultaccount.setAmount(defaultaccount.getAmount() - dto.getInitdeposit());
        DepositEntity defaultDeposit = DepositEntity.builder()
                .accountnum(defaultaccount.getAccountnum())
                .iotype(DepositType.WITHDRAW)
                .savingtype(SavingType.DEFAULT)
                .amount(dto.getInitdeposit())
                .build();



        AccountEntity accountEntity = AccountEntity.builder()
                .accountnum(CustomUtil.GenerateAccountNumber())
                .userid(dto.getUserid())
                .savingtype(dto.getType())
                .interest(0)
                .interestrate(interestrate)
                .amount(dto.getInitdeposit())
                .start_time(cur)
                .end_time(cur+dto.getPeriod()*2)
                .build();

        DepositEntity depositEntity = DepositEntity.builder()
                .accountnum(accountEntity.getAccountnum())
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

//    @Scheduled(fixedDelay = 5 * 60 * 1000)
//    @Transactional
//    public void UpdateInterest(){
//        accountRepository.updateInterestAll();
//    }
    @Scheduled(fixedDelay = 60 * 1000)
    @Transactional
    public void processAccount() {

        accountRepository.updateInterest();   // 이자 지급

        accountRepository.transferExpired(ExternalAPI.GetDollorPrice());  // 이관
        accountRepository.deleteExpired();     // 삭제
    }

    private String CalcTime(int time){
        int hour = time/ 60;
        int minute = time%60;
        return String.format("%02d:%02d", hour, minute);
    }

}

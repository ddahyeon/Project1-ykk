package ykk.ykk_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ykk.ykk_backend.common.CustomUtil;
import ykk.ykk_backend.common.SavingType;
import ykk.ykk_backend.dto.ReqLogin;
import ykk.ykk_backend.dto.ReqSignup;
import ykk.ykk_backend.entity.AccountEntity;
import ykk.ykk_backend.entity.UserEntity;
import ykk.ykk_backend.repository.AccountRepository;
import ykk.ykk_backend.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;


    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String signup(ReqSignup reqSignup) {
        UserEntity userEntity = new UserEntity(
                reqSignup.getUserid(),
                encoder.encode(reqSignup.getPassword()),
                reqSignup.getUsername(),
                reqSignup.getBirthday());

        AccountEntity accountEntity = AccountEntity.builder()
                .userid(reqSignup.getUserid())
                .accountnum(CustomUtil.GenerateAccountNumber())
                .savingtype(SavingType.DEFAULT)
                .interest(0)
                .interestrate(0)
                .amount(0)
                .build();


        userRepository.save(userEntity);
        accountRepository.save(accountEntity);
        return "good";
    }

    @Override
    public String login(ReqLogin reqLogin) {
        UserEntity userEntity = userRepository.findById(reqLogin.getUserid())
                .orElseThrow(()-> new RuntimeException("존재하지 않는 아이디 입니다."));
        if (!encoder.matches(reqLogin.getPassword(), userEntity.getPassword())) {
            throw new RuntimeException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        return "success";

//        String jwt = jwtProvider.createToken(userEntity);
//
//        return ResponseEntity.ok(new LoginResponse(jwt));
    }
}

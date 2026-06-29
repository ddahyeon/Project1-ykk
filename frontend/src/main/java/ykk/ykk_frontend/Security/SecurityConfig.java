package ykk.ykk_frontend.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // csrf 비활성화 ( 실무에서는 활성화 권장 )
        http.csrf(csrf -> csrf.disable());

        // 인증하기 위한 로그인 화면 구성하기
        http.formLogin(form -> form.disable());

        return http.build();
    }
    // 암호화할 때 사용하는 빈
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}

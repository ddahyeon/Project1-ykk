package ykk.ykk_frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    // ==========================================
    // 1. 서비스 진입점 (로그인 & 회원가입)
    // ==========================================

    @GetMapping("/")
    public String home() {
        // 기존에 중복 매핑 에러를 방지하기 위해 기존 HomeController의 "/" 매핑은 주석 처리하거나 지워주세요! [cite: 24]
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    // ==========================================
    // 2. 메인 페이지 (대시보드)
    // ==========================================

    @GetMapping("/main")
    public String mainPage() {
        // 💡 주의: 실제 운영 환경에서는 데이터베이스의 계좌 잔액(amount)을 불러와 Model에 담아주는 AssetService 등의 로직이 별도 MainController에 구현되어야 합니다. [cite: 102, 103, 115]
        // 여기서는 화면 이동 테스트를 위한 뷰 매핑만 제공합니다.
        return "main";
    }

    // ==========================================
    // 3. 뱅킹 기능 (통합 입출금 페이지)
    // ==========================================

    @GetMapping("/banking/transfer")
    public String transfer() {
        return "banking/transfer";
    }

    // ==========================================
    // 4. 금융 상품 리스트 및 가입 폼
    // ==========================================

    @GetMapping("/products")
    public String products() {
        return "products";
    }

    @GetMapping("/join-product")
    public String joinProduct() {
        return "join-form";
    }
}
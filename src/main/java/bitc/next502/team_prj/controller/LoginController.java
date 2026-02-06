package bitc.next502.team_prj.controller;

import bitc.next502.team_prj.dto.BusinessUserDTO;
import bitc.next502.team_prj.dto.NormalUserDTO;
import bitc.next502.team_prj.service.LoginServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private final LoginServiceImpl loginService;

    public LoginController(LoginServiceImpl loginServiceImpl) {
        this.loginService = loginServiceImpl;
    }

    // 로그인 페이지 이동
    @GetMapping("/login")
    public String loginPage() {
        return "login/login";
    }

    // 로그인 처리
    @PostMapping("/loginProcess")
    public String loginProcess(String username,
                                String password,
                                HttpSession session
                                , RedirectAttributes rttr) {

        Object user = loginService.login(username, password);

        if (user == null) {
            rttr.addFlashAttribute("errorMsg", "아이디 또는 비밀번호를 확인해주세요.");
            return "redirect:/login";
        }


        session.setAttribute("loginUser", user);

        if (user instanceof NormalUserDTO) {
            session.setAttribute("role", "NORMAL");
        }

        if (user instanceof BusinessUserDTO) {
            session.setAttribute("role", "BUSINESS");
        }

        return "redirect:/main";


    }
    // 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 세션 정보 전체 삭제
        session.invalidate();

        // 로그아웃 후 메인 페이지나 로그인 페이지로 리다이렉트
        return "redirect:/login";
    }

//  아이디 찾기 페이지 이동
    @GetMapping("/user/findId")
    public String findIdPage() {
        return "login/findId";
    }

    // 비밀번호 찾기 페이지 이동
    @GetMapping("/user/findPw")
    public String findPwPage() {
        return "login/findPw";
    }

// 아이디 찾기
    @PostMapping("/user/findId")
    public String findIdSubmit(@RequestParam String name,
                               @RequestParam String phone,
                               RedirectAttributes rttr,
                               Model model) {

        NormalUserDTO normalUser = loginService.findNormalUserByNameAndPhone(name, phone);

        BusinessUserDTO businessUser = loginService.findBusinessUserByNameAndPhone(name, phone);

        if (normalUser != null) {
            model.addAttribute("foundId", normalUser.getUserId());
        } else if (businessUser != null) {
            model.addAttribute("foundId", businessUser.getBusinessId());
        } else {
            model.addAttribute("error", "일치하는 사용자가 없습니다.");
        }

        return "login/findIdResult";
    }

//  비밀번호 찾기

    @PostMapping("/user/findPw")
    public String findPwSubmit(@RequestParam String id,
                               @RequestParam String name,
                               @RequestParam String phone,
                               Model model) {


        NormalUserDTO normalUser = loginService.findNormalUserByIdNamePhone(id, name, phone);


        BusinessUserDTO businessUser = loginService.findBusinessUserByIdNamePhone(id, name, phone);

        if (normalUser != null) {
            model.addAttribute("userType", "NORMAL");
            model.addAttribute("userId", normalUser.getUserId());
        } else if (businessUser != null) {
            model.addAttribute("userType", "BUSINESS");
            model.addAttribute("userId", businessUser.getBusinessId());
        } else {
            model.addAttribute("error", "입력하신 정보와 일치하는 사용자가 없습니다.");
        }

        return "login/findPwResult";
    }
}

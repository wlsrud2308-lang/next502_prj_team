package bitc.next502.team_prj.controller;

import bitc.next502.team_prj.dto.BusinessUserDTO;
import bitc.next502.team_prj.dto.NormalUserDTO;
import bitc.next502.team_prj.service.SignupServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class SignupController {

    private final SignupServiceImpl signupService;

    // 회원가입 페이지 이동
    @GetMapping("/user/signup")
    public String signupPage() {
        return "user/signup";
    }

    // 회원가입 처리
    @PostMapping("/signupProcess")
    public String signupProcess(
            @RequestParam String userType,

            @RequestParam String id,
            @RequestParam String pw,


            @RequestParam(required = false) String userEmail,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String userTel,
            @RequestParam(required = false) Integer userAge,

            @RequestParam(required = false) String businessName,
            @RequestParam(required = false) String businessNumber,
            @RequestParam(required = false) String businessTel
    ) {

        if ("normal".equals(userType)) {

            NormalUserDTO dto = new NormalUserDTO();
            dto.setUserEmail(userEmail);
            dto.setUserId(id);
            dto.setUserPw(pw);
            dto.setName(userName);
            dto.setPhone(userTel);
            dto.setAge(userAge);

            signupService.signupNormalUser(dto);
        }

        else if ("business".equals(userType)) {

            BusinessUserDTO dto = new BusinessUserDTO();
            dto.setBusinessId(id);
            dto.setBusinessPwd(pw);
            dto.setBusinessName(businessName);
            dto.setBusinessNumber(businessNumber);
            dto.setBusinessPhone(businessTel);

            signupService.signupBusinessUser(dto);
        }

        return "redirect:/login";
    }
}

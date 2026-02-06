package bitc.next502.team_prj.controller;

import bitc.next502.team_prj.dto.BusinessUserDTO;
import bitc.next502.team_prj.dto.MyInfoDTO;
import bitc.next502.team_prj.dto.MyResvDTO;
import bitc.next502.team_prj.dto.NormalUserDTO;
import bitc.next502.team_prj.service.MyPageServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired
    private MyPageServiceImpl myPageService;

    // 1. 메인 화면 보여주기
    @GetMapping("/main")
    public String myPageMain(Model model, HttpServletRequest request) {

        // [나중에 합칠 때] 밑에꺼로
        HttpSession session = request.getSession();
        Object userBoxing = session.getAttribute("loginUser");

        String userId = null;

        String role = (String) session.getAttribute("role");

        if (role.equals("NORMAL")) {
            NormalUserDTO normalUser = (NormalUserDTO) userBoxing;
            userId = normalUser.getUserId();
        }
        else if (role.equals("BUSINESS")) {
            BusinessUserDTO businessUser = (BusinessUserDTO) userBoxing;
            userId = businessUser.getBusinessId();
        }
        // -------------------------------------------------------
//        String userId = "2"; // 현재는 2번 회원으로 고정 테스트  합치면 이거 삭제

        // 데이터 가져오기
        MyInfoDTO userInfo = myPageService.getMyInfo(userId);
        List<MyResvDTO> resvList = myPageService.getMyResvList(userId);

        // 화면으로 보내기
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("resvList", resvList);

        return "mypage/mypage";
    }

    // 2. 예약 취소 기능
    @GetMapping("/delete")
    public String deleteReservation(@RequestParam("resvId") int resvId) {

        // 서비스에게 삭제 명령
        myPageService.cancelReservation(resvId);

        // 삭제 후 메인으로 돌아가기
        return "redirect:/mypage/main";
    }
}
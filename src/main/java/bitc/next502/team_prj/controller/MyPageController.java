package bitc.next502.team_prj.controller;

import bitc.next502.team_prj.dto.*;
import bitc.next502.team_prj.service.BookmarkService;
import bitc.next502.team_prj.service.MyPageServiceImpl;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired
    private MyPageServiceImpl myPageService;

    @Autowired
    private BookmarkService bookmarkService;

    // 1. 메인 화면 (나의 예약 내역)
    @GetMapping("/main")
    public String myPageMain(@RequestParam(required = false,defaultValue="1",value="pageNum") int pageNum,
                             Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object userBoxing = session.getAttribute("loginUser");
        String role = (String) session.getAttribute("role");
        String userId = getUserIdFromSession(userBoxing, role);

        if (userId == null) return "redirect:/user/login";

        model.addAttribute("userId", userId);

        MyInfoDTO userInfo = myPageService.getMyInfo(userId);
        if (userInfo == null) {
            userInfo = new MyInfoDTO();
            userInfo.setName("회원");
            userInfo.setGrade("일반");
        }
        model.addAttribute("userInfo", userInfo);

        int navigatePages = 5;
        PageInfo<MyResvDTO> resvList = new PageInfo<>(myPageService.getMyResvList(pageNum, userId), navigatePages);
        model.addAttribute("resvList", resvList);

        List<BookmarkDTO> bookmarkList = bookmarkService.getBookmarkList(userId);
        model.addAttribute("bookmarkList", bookmarkList);

        return "mypage/mypage";
    }

    // 2. 예약 취소
    @GetMapping("/delete")
    public String deleteReservation(@RequestParam("resvId") int resvId) {
        myPageService.cancelReservation(resvId);
        return "redirect:/mypage/main";
    }

    // 3. 관심 식당 리스트
    @GetMapping("/mybookmarkList")
    public String myBookmarkList(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object userBoxing = session.getAttribute("loginUser");
        String role = (String) session.getAttribute("role");
        String userId = getUserIdFromSession(userBoxing, role);

        if (userId == null) return "redirect:/user/login";

        MyInfoDTO userInfo = myPageService.getMyInfo(userId);
        if (userInfo == null) {
            userInfo = new MyInfoDTO();
            userInfo.setName("회원");
        }
        model.addAttribute("userInfo", userInfo);

        List<BookmarkDTO> bookmarkList = bookmarkService.getBookmarkList(userId);
        model.addAttribute("bookmarkList", bookmarkList);

        return "mypage/mybookmarkList";
    }

    @GetMapping("/mypageList")
    public String mypageList(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object userBoxing = session.getAttribute("loginUser");
        String role = (String) session.getAttribute("role");
        String userId = getUserIdFromSession(userBoxing, role);

        if (userId == null) return "redirect:/user/login";

        MyInfoDTO userInfo = myPageService.getMyInfo(userId);
        if (userInfo == null) {
            userInfo = new MyInfoDTO();
            userInfo.setName("회원");
        }
        model.addAttribute("userInfo", userInfo);

        List<BookmarkDTO> bookmarkList = bookmarkService.getBookmarkList(userId);
        model.addAttribute("bookmarkList", bookmarkList);

        return "mypage/mypageList";
    }


    @PostMapping("/deleteAccount")
    public String deleteNormalUserAccount(HttpSession session, @RequestParam String password, RedirectAttributes redirectAttributes) {
        Object userBoxing = session.getAttribute("loginUser");
        String role = (String) session.getAttribute("role");

        if (userBoxing == null || role == null || !"NORMAL".equals(role)) {
            return "redirect:/user/login";
        }

        NormalUserDTO normalUser = (NormalUserDTO) userBoxing;
        boolean deleted = myPageService.deleteNormalUserAccount(normalUser.getUserId(), password);

        if (deleted) {
            session.invalidate();
            redirectAttributes.addFlashAttribute("alertMessage", "회원 탈퇴가 완료되었습니다.");
            redirectAttributes.addFlashAttribute("alertType", "success");
            return "redirect:/main";
        } else {
            redirectAttributes.addFlashAttribute("alertMessage", "비밀번호가 올바르지 않습니다.");
            redirectAttributes.addFlashAttribute("alertType", "danger");
            return "redirect:/mypage/main";
        }
    }


    private String getUserIdFromSession(Object userBoxing, String role) {
        if (userBoxing == null || role == null) return null;
        if ("NORMAL".equals(role)) return ((NormalUserDTO) userBoxing).getUserId();
        if ("BUSINESS".equals(role)) return ((BusinessUserDTO) userBoxing).getBusinessId();
        return null;
    }

    @PostMapping("/updateInfo")
    public String updateInfo(MyInfoDTO updatedInfo, RedirectAttributes redirectAttributes) {
        try {
            myPageService.updateMyInfo(updatedInfo);

            redirectAttributes.addFlashAttribute("alertMessage", "개인정보가 수정되었습니다.");
            redirectAttributes.addFlashAttribute("alertType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("alertMessage", "수정 중 오류가 발생했습니다.");
            redirectAttributes.addFlashAttribute("alertType", "danger");
        }

        return "redirect:/mypage/mypageList";
    }
}
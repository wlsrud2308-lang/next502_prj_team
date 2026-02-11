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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired
    private MyPageServiceImpl myPageService;

    @Autowired
    private BookmarkService bookmarkService;

    // 1. 메인 화면
    @GetMapping("/main")
    public String myPageMain(@RequestParam(required = false,defaultValue="1",value="pageNum") int pageNum,
                             Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object userBoxing = session.getAttribute("loginUser");
        String role = (String) session.getAttribute("role");
        String userId = null;

        if (userBoxing == null) return "redirect:/user/login";

        if (role != null && role.equals("NORMAL")) {
            userId = ((NormalUserDTO) userBoxing).getUserId();
        } else if (role != null && role.equals("BUSINESS")) {
            userId = ((BusinessUserDTO) userBoxing).getBusinessId();
        }

        model.addAttribute("userId", userId); // 아이디 전달

        MyInfoDTO userInfo = myPageService.getMyInfo(userId);
        if (userInfo == null) {
            userInfo = new MyInfoDTO();
            userInfo.setName("회원");
            userInfo.setGrade("일반");
        }
        model.addAttribute("userInfo", userInfo);

        //List<MyResvDTO> resvList = myPageService.getMyResvList(userId);
        //페이징처리
        int navigatePages = 5; //화면에 보여지는 페이지버튼수
        PageInfo<MyResvDTO> resvList = new PageInfo<>( myPageService.getMyResvList(pageNum,userId), navigatePages);



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

    // 3. 관심 식당
    @GetMapping("/mybookmarkList")
    public String myBookmarkList(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object userBoxing = session.getAttribute("loginUser");
        String role = (String) session.getAttribute("role");
        String userId = null;

        if (userBoxing == null) return "redirect:/user/login";

        if (role != null && role.equals("NORMAL")) {
            userId = ((NormalUserDTO) userBoxing).getUserId();
        } else if (role != null && role.equals("BUSINESS")) {
            userId = ((BusinessUserDTO) userBoxing).getBusinessId();
        }

        model.addAttribute("userId", userId); // 아이디 전달

        MyInfoDTO userInfo = myPageService.getMyInfo(userId);
        if (userInfo == null) {
            userInfo = new MyInfoDTO();
            userInfo.setName("회원");
            userInfo.setGrade("일반");
        }
        model.addAttribute("userInfo", userInfo);

        List<BookmarkDTO> bookmarkList = bookmarkService.getBookmarkList(userId);
        model.addAttribute("bookmarkList", bookmarkList);

        return "mypage/mybookmarkList";
    }
}
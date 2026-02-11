package bitc.next502.team_prj.controller;

import bitc.next502.team_prj.dto.NormalUserDTO;
import bitc.next502.team_prj.service.BookmarkService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;


    @ResponseBody
    @PostMapping("/bookmark/toggle")
    public String bookmarkToggle(@RequestParam("restaurantId") String restaurantId,
                                 @RequestParam("restaurantName") String restaurantName,
                                 HttpSession session) {

        // 1. 세션에서 로그인 정보 가져오기
        Object user = session.getAttribute("loginUser");

        if (user == null) {
            return "login-required";
        }

        String userId = null;
        if (user instanceof NormalUserDTO) {
            userId = ((NormalUserDTO) user).getUserId();
        }

        if (userId == null) {
            return "error";
        }

        int result = bookmarkService.toggleBookmark(userId, restaurantId, restaurantName);
        return String.valueOf(result);
    }
    @GetMapping("/bookmark/delete")
    public String deleteBookmark(@RequestParam("restaurantId") String restaurantId,
                                 HttpSession session) {

        // 1. 로그인 유저 확인
        Object user = session.getAttribute("loginUser");
        if (user == null) {
            return "redirect:/user/login";
        }

        // 2. ID 꺼내기 (일반회원 기준)
        String userId = null;
        if (user instanceof NormalUserDTO) {
            userId = ((NormalUserDTO) user).getUserId();
        }

        // 3. 삭제 서비스 실행
        if (userId != null) {
            bookmarkService.deleteBookmark(userId, restaurantId);
        }

        // 4. 다시 목록 페이지로 돌아가기 (새로고침 효과)
        return "redirect:/mypage/mybookmarkList";
    }
}
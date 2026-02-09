package bitc.next502.team_prj.controller;

import bitc.next502.team_prj.dto.*;
import bitc.next502.team_prj.service.MngService;
import bitc.next502.team_prj.service.RestaurantService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class MngController {

    @Autowired
    private MngService mngService;

    // 예약자 명단 관리
    @GetMapping("/mngmenu")
    public String mngMenu(Model model, HttpSession session) {

        String role = (String) session.getAttribute("role");
        Object userBoxing = session.getAttribute("loginUser");

        if (role == null || userBoxing == null) {
            return "redirect:/login";
        }

        if (!"BUSINESS".equals(role)) {
            return "redirect:/login";
        }

        BusinessUserDTO businessUser = (BusinessUserDTO) userBoxing;
        String businessId = businessUser.getBusinessId();

        MngDTO mng = mngService.getMngInfo(businessId);
        List<MngDTO> resvList = mngService.getResvList(businessId);

        model.addAttribute("mng", mng);
        model.addAttribute("resvList", resvList);
        
        return "mng/mngmenu";
    }

    @GetMapping("/mngstoreWrite")
    public String mngstoreWrite() {
        return "mng/mngstoreWrite";
    }

    @GetMapping("/mngreview")
    public String mngreview() {
        return "mng/mngreview";
    }

//    // 식당 정보 등록
//    @GetMapping("/mngstoreWrite")
//    public String mngStoreWrite(Model model) {
//        model.addAttribute("menuId", "store"); // 'store'라는 별명을 붙임
//        return "mng/mngstoreWrite";
//    }
//
//    // 리뷰 답변 관리
//    @GetMapping("/mngreview")
//    public String mngReview(Model model) {
//        model.addAttribute("menuId", "review"); // 'review'라는 별명을 붙임
//        return "mng/mngreview";
//    }
}

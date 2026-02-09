package bitc.next502.team_prj.controller;

import bitc.next502.team_prj.dto.*;
import bitc.next502.team_prj.service.MngService;
import bitc.next502.team_prj.service.RestaurantService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MngController {

    @Autowired
    private MngService mngService;

    @GetMapping("/mngstoreWrite")
    public String mngstoreWrite() {
        return "mng/mngstoreWrite";
    }

    // 예약자 명단 관리
    @GetMapping("/mngmenu")
    public String mngMenu(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        Object userBoxing = session.getAttribute("loginUser");

        String businessId = null;

        String role = (String) session.getAttribute("role");

        if (role.equals("BUSINESS")) {
            BusinessUserDTO businessUser = (BusinessUserDTO) userBoxing;
            businessId = businessUser.getBusinessId();
        }
        else if (role.equals("NORMAL")) {
            NormalUserDTO normalUser = (NormalUserDTO) userBoxing;
            businessId = normalUser.getUserId();
        }

        MngDTO mng = mngService.getMngInfo(businessId);
        List<MngDTO> resvList = mngService.getResvList(businessId);

        model.addAttribute("mng", mng);
        model.addAttribute("resvList", resvList);
        
        return "mng/mngmenu";
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

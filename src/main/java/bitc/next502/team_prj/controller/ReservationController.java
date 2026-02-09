package bitc.next502.team_prj.controller;

import bitc.next502.team_prj.dto.ReservationDTO;
import bitc.next502.team_prj.dto.RestaurantDTO;
import bitc.next502.team_prj.service.ReservationService;
import bitc.next502.team_prj.service.RestaurantService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    private final RestaurantService restaurantService;

    @GetMapping("/write")
    public String resvWrite(@RequestParam("restaurantId") String restaurantId,
                            HttpSession session,
                            Model model) {


        if (session.getAttribute("loginUser") == null) {

            model.addAttribute("msg", "로그인 후 이용 가능합니다.");
            model.addAttribute("url", "/login");
            return "common/alert";
        }


        String cleanId = restaurantId.replace("\"", "");
        RestaurantDTO restaurant = restaurantService.getRestaurantDetail(cleanId);

        model.addAttribute("restaurant", restaurant);
        model.addAttribute("restaurantId", cleanId);

        return "reservation/resvWrite";
    }


    @PostMapping("/insert")
    public String resvInsert(ReservationDTO reservation, Model model) {
        try {
            reservationService.saveReservation(reservation);
            model.addAttribute("msg", "예약이 성공적으로 확정되었습니다."); // 성공 메시지
            model.addAttribute("url", "/main");
            return "common/alert";
        } catch (Exception e) {
            // DB 에러(외래키 등)가 나면 이쪽으로 옵니다.

            e.printStackTrace();
            model.addAttribute("msg", "예약 처리 중 오류가 발생했습니다. 정보를 다시 확인해 주세요.");
            model.addAttribute("url", "javascript:history.back();");
            return "common/alert";
        }
    }
}
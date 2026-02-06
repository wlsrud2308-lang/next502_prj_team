package bitc.next502.team_prj.controller;

import bitc.next502.team_prj.dto.ReservationDTO;
import bitc.next502.team_prj.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;


    @GetMapping("/write")
    public String resvWrite(@RequestParam("restaurantId") String restaurantId, Model model) {
        model.addAttribute("restaurantId", restaurantId);
        return "reservation/resvWrite";
    }

    @PostMapping("/insert")
    public String resvInsert(ReservationDTO reservation) {
        reservationService.saveReservation(reservation);
        return "redirect:/";
    }
}
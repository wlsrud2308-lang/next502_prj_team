package bitc.next502.team_prj.controller;

import bitc.next502.team_prj.dto.RatingStatDTO;
import bitc.next502.team_prj.dto.ReviewDTO;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import bitc.next502.team_prj.service.RestaurantService;
import bitc.next502.team_prj.dto.RestaurantDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class MainController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/main")
    public String mainPage(Model model) {
        // 상위 3개 데이터 가져오기
        List<RestaurantDTO> topList = restaurantService.getTop3();
        model.addAttribute("topList", topList);
        return "store/main";
    }

//    @GetMapping("/login")
//    public String loginForm() {
//        return "login/login";
//    }

//    @GetMapping("/mypage")
//    public String mypage() {
//        return "mypage/mypage";
//    }

//    @PostMapping("/loginProcess")
//    public String loginProcess(
//            @RequestParam String username,
//            @RequestParam String password
//    ) {
//        return "redirect:/main";
//    }






    @GetMapping("/detail")
    public String detail(@RequestParam("id") String id, Model model) {
        RestaurantDTO restaurant = restaurantService.getRestaurantDetail(id);
        List<ReviewDTO> reviewList = restaurantService.getReviewsByRestaurantId(id);
        int reviewCount = restaurantService.getReviewCountByRestaurantId(id);

        List<RatingStatDTO> stats = restaurantService.getRatingStatsByRestaurantId(id);
        Map<Integer, Integer> ratingCounts = new HashMap<>();
        for (int r = 1; r <= 5; r++) ratingCounts.put(r, 0);
        for (RatingStatDTO s : stats) ratingCounts.put(s.getRating(), s.getCount());

        model.addAttribute("restaurant", restaurant);
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("reviewCount", reviewCount);
        model.addAttribute("ratingCounts", ratingCounts);

        return "store/detail";
    }
}


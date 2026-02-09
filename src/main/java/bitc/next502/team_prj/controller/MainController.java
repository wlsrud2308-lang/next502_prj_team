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

    @GetMapping({"/main","/"})
    public String mainPage(Model model) {
        // 상위 3개 데이터 가져오기
        List<RestaurantDTO> topList = restaurantService.getTop3();
        model.addAttribute("topList", topList);
        return "store/main";
    }

    @GetMapping("/mybookmarkList")
    public String mybookmarkList() {
        return "mypage/mybookmarkList";
    }

    @GetMapping("/myreviewList")
    public String myreviewList() {
        return "mypage/myreviewList";
    }

    @GetMapping("/mypageList")
    public String mypageList() {
        return "mypage/mypageList";
    }


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

//    @GetMapping("/store/search")
//    public String searchRestaurants(@RequestParam(value = "keyword", required = false) String keyword,
//                                    Model model) {
//
//        List<RestaurantDTO> resList = restaurantService.searchByKeyword(keyword);
//
//        model.addAttribute("resList", resList);
//        model.addAttribute("keyword", keyword);
//
//        return "store/searchList";
//    }
@GetMapping("/store/search")
public String searchRestaurants(
        @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
        @RequestParam(value = "locations", required = false) List<String> locations,
        @RequestParam(value = "categories", required = false) List<String> categories,
        @RequestParam(value = "sort", required = false, defaultValue = "rating") String sort,
        Model model) {

    List<RestaurantDTO> resList = restaurantService.searchByFilter(keyword, locations, categories, sort);

    model.addAttribute("resList", resList);
    model.addAttribute("keyword", keyword);
    model.addAttribute("selectedLocs", locations);
    model.addAttribute("selectedCats", categories);
    model.addAttribute("selectedSort", sort);

    return "store/searchList";
}
}

